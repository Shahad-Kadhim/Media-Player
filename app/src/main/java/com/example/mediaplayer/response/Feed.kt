package com.example.mediaplayer.response

data class Feed(
    val description: String,
    val id: String,
    val image: String,
    val items: List<Item>,
    val title: String
)