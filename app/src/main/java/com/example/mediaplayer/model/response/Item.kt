package com.example.mediaplayer.model.response

data class Item(
    val art: String,
    val description: String,
    val director: String,
    val duration: Int,
    val id: String,
    val ratings: Any,
    val title: String,
    val url: String,
    val year: Int
)