package com.henry.appnews.ui.fragment.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.ui.fragment.favorite.FavoriteViewModel
import com.henry.appnews.ui.fragment.home.HomeViewModel
import com.henry.appnews.ui.fragment.search.SearchViewModel
import com.henry.appnews.ui.fragment.webview.WebViewViewModel

class ViewModelFactory(
    private val repository: NewsRepository,
    private val application: Application
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel(repository, application) as T
            modelClass.isAssignableFrom(SearchViewModel::class.java) -> SearchViewModel(repository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> FavoriteViewModel(
                repository
            ) as T
            modelClass.isAssignableFrom(WebViewViewModel::class.java) -> WebViewViewModel(repository) as T
            else -> throw IllegalAccessException("ViewModel não encontrado")
        }
    }
}