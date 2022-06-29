package com.henry.appnews.repository

import android.content.Context
import com.henry.appnews.data.local.model.Article
import com.henry.appnews.data.local.db.ArticleDataBase
import com.henry.appnews.data.remote.RetrofitInstance
import com.henry.appnews.presenter.favorite.FavoriteHome
import com.henry.appnews.presenter.news.NewsHome
import com.henry.appnews.presenter.search.SearchHome
import kotlinx.coroutines.*

class NewsDataSource(context: Context) {

    private var db: ArticleDataBase = ArticleDataBase(context)
    private var newsRepository: NewsRepository = NewsRepository(RetrofitInstance.api, db)

    fun getBreakingNews(callback: NewsHome.Presenter){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.getBraakingNews("br")
            if(response.isSuccessful){
                response.body()?.let { newsResponse ->
                    callback.onSucess(newsResponse)
                }
                callback.onComplete()
            }else{
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun searchNews(term: String, callback: SearchHome.Presenter){
        GlobalScope.launch(Dispatchers.Main) {
            val response = RetrofitInstance.api.searchNews(term)
            if (response.isSuccessful){
                response.body()?.let {
                    callback.onSucess(it)
                }
                callback.onComplete()
            }else{
                callback.onError(response.message())
                callback.onComplete()
            }
        }
    }

    fun saveArticle(article: Article){
        GlobalScope.launch(Dispatchers.Main) {
            newsRepository.updateInsert(article)
        }
    }

    fun getAllArticle(callback: FavoriteHome.Presenter){
        var allArticle: List<Article>
        CoroutineScope(Dispatchers.IO).launch {
            allArticle = newsRepository.getAll().value!!

            withContext(Dispatchers.Main){
                callback.onSuccess(allArticle)
            }
        }
    }
    fun deleteArticle(article: Article?){
        GlobalScope.launch (Dispatchers.Main){
            article?.let { articleDeleted ->
                newsRepository.delete(articleDeleted)

            }
        }
    }
}