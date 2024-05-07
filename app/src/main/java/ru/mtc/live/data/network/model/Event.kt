package ru.mtc.live.data.network.model

data class Event(
    val id: Int,
    val name: String,
    val image: String?,
    val categories: List<Category>
)

data class Category(
    val id: Int,
    val name: String
)