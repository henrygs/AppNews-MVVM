package com.henry.appnews.presenter.search

import com.henry.appnews.data.local.model.NewsResponse

interface SearchHome {

    interface Presenter{
        fun search(term: String)
        fun onSucess(newsResponse: NewsResponse)
        fun onError(message: String)
        fun onComplete()
    }
}