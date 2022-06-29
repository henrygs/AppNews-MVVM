package com.henry.appnews.presenter.favorite

import com.henry.appnews.data.local.model.Article

interface FavoriteHome {

    interface Presenter{
        fun onSuccess(articles: List<Article>)
    }
}