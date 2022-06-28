package com.henry.appnews.model.data

import com.henry.appnews.model.Article
import com.henry.appnews.model.db.ArticleDataBase

class NewsRepository(private val db: ArticleDataBase) {

    suspend fun updateInsert(article: Article) = db.getArticleDao().updateInsert(article)

    fun getAll(): List<Article> = db.getArticleDao().getAll()

    suspend fun delete(article: Article) = db.getArticleDao().delete(article)

}