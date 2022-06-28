package com.henry.appnews.presenter.search

import com.henry.appnews.model.NewsResponse
import com.henry.appnews.model.data.NewsDataSource
import com.henry.appnews.presenter.ViewHome

class SearchPresenter(
    val view:ViewHome.View,
    private val dataSource: NewsDataSource
    ): SearchHome.Presenter {


    override fun search(term: String) {
        this.view.showProgressBar()
        this.dataSource.searchNews(term, this)
    }

    override fun onSucess(newsResponse: NewsResponse) {
        this.view.showArticles(newsResponse.articles)
    }

    override fun onError(message: String) {
        this.view.showFailure(message)
    }

    override fun onComplete() {
        this.view.hideProgressBar()
    }
}