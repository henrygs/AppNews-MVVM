package com.henry.appnews.utils.state

sealed class ArticlListEvent {
    object Fetch: ArticlListEvent()
}