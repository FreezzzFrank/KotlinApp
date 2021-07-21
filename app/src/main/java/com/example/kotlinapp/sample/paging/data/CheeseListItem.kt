package com.example.kotlinapp.sample.paging.data

sealed class CheeseListItem(val name: String) {
    data class ListItem(val cheese: Cheese) : CheeseListItem(cheese.name)
    data class Seprator(private val letter: Char) : CheeseListItem(letter.uppercase())
}
