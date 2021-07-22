package com.example.kotlinapp.sample.paging.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.kotlinapp.sample.paging.data.Cheese
import com.example.kotlinapp.sample.paging.data.CheeseListItem
import com.example.kotlinapp.sample.paging.data.CheeseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class CheeseViewModel @Inject constructor(
    private val cheeseRepository: CheeseRepository
) : ViewModel() {
    val allCheeses: Flow<PagingData<CheeseListItem>> = Pager(
        config = PagingConfig(
            pageSize = 60,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        cheeseRepository.allCheeses()
    }.flow
        .map { pagingData ->
            pagingData
                .map { cheese -> CheeseListItem.ListItem(cheese) }
                .insertSeparators { before: CheeseListItem?, after: CheeseListItem? ->
                    if (before == null && after == null) {
                        null
                    } else if (after == null) {
                        null
                    } else if (before == null) {
                        CheeseListItem.Seprator(after.name.first())
                    } else if (!before.name.first().equals(after.name.first(), ignoreCase = true)) {
                        CheeseListItem.Seprator(after.name.first())
                    } else {
                        null
                    }
                }

        }.cachedIn(viewModelScope)

    fun setCheese(text: CharSequence) {
        val inputString = text.trim()
        if (inputString.isNotEmpty()) {
            addCheese(inputString)
        }
    }

    fun removeCheese(cheese: Cheese) {
        cheeseRepository.remove(cheese)
    }

    private fun addCheese(text: CharSequence) {
        cheeseRepository.insert(text)
    }
}