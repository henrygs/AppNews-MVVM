package com.henry.appnews.repository

import androidx.lifecycle.LiveData
import com.henry.appnews.data.local.model.Article
import com.henry.appnews.data.local.db.ArticleDataBase
import com.henry.appnews.data.remote.NewsApi

class NewsRepository(
    private val api: NewsApi,
    private val db: ArticleDataBase

) {

    suspend fun getAllRemote() = api.getBraakingNews()
    suspend fun search(query: String) = api.searchNews(query)

    fun getAll(): LiveData<List<Article>> = db.getArticleDao().getAll()
    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)
    suspend fun delete(article: Article) = db.getArticleDao().delete(article)
}