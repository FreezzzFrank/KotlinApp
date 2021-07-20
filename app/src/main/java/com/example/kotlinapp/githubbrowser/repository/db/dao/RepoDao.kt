package com.example.kotlinapp.githubbrowser.repository.db.dao

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinapp.data.local.dao.BaseDao
import com.example.kotlinapp.githubbrowser.repository.db.entities.Contributor
import com.example.kotlinapp.githubbrowser.repository.db.entities.Repo
import com.example.kotlinapp.githubbrowser.repository.db.entities.RepoSearchResult

@Dao
abstract class RepoDao : BaseDao<Repo>{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertContributors(contributors: List<Contributor>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRepos(repositories: List<Repo>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun createRepoIfNotExists(repo: Repo): Long

    @Query("SELECT * FROM repo WHERE owner_login = :ownerLogin AND name = :name")
    abstract fun load(ownerLogin: String, name: String): LiveData<Repo>

    @Query("""
            SELECT login, avatarUrl, repoName, repoOwner, contributions FROM contributor
            WHERE repoName = :name AND repoOwner = :owner ORDER BY contributions DESC
            """)
    abstract fun loadContributors(owner: String, name: String): LiveData<List<Contributor>>

    @Query("SELECT * FROM repo WHERE owner_login = :owner ORDER BY stars DESC")
    abstract fun loadRepositories(owner: String): LiveData<List<Repo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSearchResult(result: RepoSearchResult)

    @Query("SELECT * FROM RepoSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<RepoSearchResult?>

    fun loadOrdered(repoIds: List<Int>): LiveData<List<Repo>> {
        val order = SparseIntArray()
        repoIds.withIndex().forEach {
            order.put(it.value, it.index)
        }

        return loadById(repoIds).map { repositories ->
            repositories.sortedWith(compareBy{ order.get(it.id) })
        }
    }

    @Query("SELECT * FROM repo WHERE id in (:repoIds)")
    protected abstract fun loadById(repoIds: List<Int>): LiveData<List<Repo>>

    @Query("SELECT * FROM RepoSearchResult WHERE 'query' = :query")
    abstract fun findSearchResult(query: String): RepoSearchResult?
}