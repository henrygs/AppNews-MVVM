package com.henry.appnews.ui.fragment.webview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.appnews.data.local.model.Article
import com.henry.appnews.repository.NewsRepository
import kotlinx.coroutines.launch

class WebViewViewModel(
    private val repository: NewsRepository
): ViewModel() {

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }
}