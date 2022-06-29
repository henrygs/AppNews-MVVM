package com.henry.appnews.data.local.db

data class NewsResponse(
    val articles: MutableList<Article>,
    val status: String?,
    val totalResults: Int
)