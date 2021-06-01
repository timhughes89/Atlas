package com.timsimonhughes.news.model

data class News(
    val id: Int,
    val imageUrl: String,
    val date: String,
    val title: String,
    val explanation: String,
    var tag: NewsType
)