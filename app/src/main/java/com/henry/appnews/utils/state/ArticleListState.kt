package com.henry.appnews.utils.state

import com.henry.appnews.data.local.model.Article

sealed class ArticleListState{
    data class Success(val list: List<Article>) : ArticleListState()
    data class ErrorMessage(val errorMessage: String) : ArticleListState()
    object Loading: ArticleListState()
    object Empty: ArticleListState()
}
