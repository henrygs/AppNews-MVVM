package com.henry.appnews.model.data

import com.henry.appnews.data.local.model.Article
import com.henry.appnews.data.local.db.ArticleDataBase

class NewsRepository(private val db: ArticleDataBase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

}