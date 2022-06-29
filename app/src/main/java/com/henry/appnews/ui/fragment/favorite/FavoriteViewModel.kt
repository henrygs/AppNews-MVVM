package com.henry.appnews.ui.fragment.favorite

import androidx.lifecycle.*
import com.henry.appnews.data.local.model.Article
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.utils.state.ArticlListEvent
import com.henry.appnews.utils.state.ArticleListState
import kotlinx.coroutines.launch

class FavoriteViewModel constructor(
    private val repository: NewsRepository
): ViewModel() {

    var _favorite = MutableLiveData<ArticlListEvent>()
    val favorite: LiveData<ArticleListState> = _favorite.switchMap {
        when(it){
            ArticlListEvent.Fetch -> getAll()
        }
    }

    fun dispatch(event: ArticlListEvent){
        this._favorite.postValue(event)
    }

    private fun getAll() : LiveData<ArticleListState>{
        return liveData {
            try {
                emit(ArticleListState.Loading)
                val listLiveData = repository.getAll()
                    .map { list ->
                        if(list.isEmpty()){
                            ArticleListState.Empty
                        }else{
                            ArticleListState.Success(list)
                        }
                    }
                emitSource(listLiveData)
            } catch (e:Exception){
                emit(ArticleListState.ErrorMessage("Algo deu errado: ${e.message}"))
            }
        }
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.updateInsert(article)
    }
    fun deliteArticle(article: Article) = viewModelScope.launch {
        repository.delete(article)
    }

}