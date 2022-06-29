package com.henry.appnews.presenter.news

import com.henry.appnews.data.local.model.NewsResponse

interface NewsHome {

    interface Presenter{
        fun requestAll()
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}