package com.example.kotlinapp.githubbrowser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import com.example.kotlinapp.data.local.AppDataBase
import com.example.kotlinapp.data.remote.NetworkBoundResource
import com.example.kotlinapp.data.remote.Resource
import com.example.kotlinapp.data.remote.api.ApiResponse
import com.example.kotlinapp.data.remote.api.ApiSuccessResponse
import com.example.kotlinapp.githubbrowser.api.GithubService
import com.example.kotlinapp.githubbrowser.repository.db.dao.RepoDao
import com.example.kotlinapp.githubbrowser.repository.db.entities.Contributor
import com.example.kotlinapp.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.githubbrowser.repository.db.entities.RepoSearchResult
import com.example.kotlinapp.githubbrowser.repository.vo.RepoSearchResponse
import com.example.kotlinapp.utilities.AbsentLiveData
import com.example.kotlinapp.utilities.AppExecutors
import com.example.kotlinapp.utilities.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: AppDataBase,
    private val repoDao: RepoDao,
    private val githubService: GithubService
) {
    private val repoListRateLimit = RateLimiter<String>(10,  TimeUnit.MINUTES)

    fun search(query: String): LiveData<Resource<List<Repo>>> =
        object : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {
            override fun saveCallResult(item: RepoSearchResponse) {

                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(query, repoIds, item.total, item.nextPage)

                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insertSearchResult(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return repoDao.search(query).switchMap { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createCall() = githubService.searchRepos(query)

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>): RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLivedata()

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query,
            githubService,
            db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }

            override fun shouldFetch(data: Repo?) = data == null

            override fun loadFromDb() = repoDao.load(owner, name)

            override fun createCall() = githubService.getRepo(owner, name)
        }.asLivedata()
    }

    fun loadContributors(owner: String, name: String): LiveData<Resource<List<Contributor>>> {
        return object : NetworkBoundResource<List<Contributor>, List<Contributor>>(appExecutors) {
            override fun saveCallResult(item: List<Contributor>) {
                item.forEach {
                    it.repoName = name
                    it.repoOwner = owner
                }
                db.runInTransaction {
                    repoDao.createRepoIfNotExists(
                        Repo(
                            id = Repo.UNKNOWN_ID,
                            name = name,
                            fullName = "$owner/$name",
                            description = "",
                            owner = Repo.Owner(owner, null),
                            stars = 0
                        )
                    )
                    repoDao.insertContributors(item)
                }
            }

            override fun shouldFetch(data: List<Contributor>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = repoDao.loadContributors(owner, name)

            override fun createCall() = githubService.getContributors(owner, name)

        }.asLivedata()
    }

    fun loadRepos(owner: String) : LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repoListRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb() = repoDao.loadRepositories(owner)

            override fun createCall() = githubService.getRepos(owner)

            override fun onFetchFailed() {
                repoListRateLimit.reset(owner)
            }
        }.asLivedata()
    }
}