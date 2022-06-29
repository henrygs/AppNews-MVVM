package com.henry.appnews.presenter.favorite

import com.henry.appnews.data.local.model.Article
import com.henry.appnews.repository.NewsDataSource
import com.henry.appnews.presenter.ViewHome

class FavoritePresenter(
    val view: ViewHome.Favorite,
    private val dataSource: NewsDataSource
) : FavoriteHome.Presenter {

    fun getAll(){
        this.dataSource.getAllArticle(this)
    }

    fun saveArticle(article: Article) {
        this.dataSource.saveArticle(article)
    }

    fun deleteArticle(article: Article){
        this.dataSource.deleteArticle(article)
    }

    override fun onSuccess(articles: List<Article>) {
        this.view.showArticles(articles)
    }
}