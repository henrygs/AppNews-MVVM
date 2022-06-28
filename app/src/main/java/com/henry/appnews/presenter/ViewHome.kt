package com.henry.appnews.presenter

import com.henry.appnews.model.Article

interface ViewHome {

    interface  View {
        fun showProgressBar()
        fun showFailure(message: String)
        fun hideProgressBar()
        fun showArticles(articles: List<Article>)
    }

    interface Favorite{
        fun showArticles(articles: List<Article>)
    }
}