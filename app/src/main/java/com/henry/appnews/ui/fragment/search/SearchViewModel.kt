package com.henry.appnews.ui.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.henry.appnews.data.local.model.NewsResponse
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.utils.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: NewsRepository): ViewModel() {

    private val _search = MutableLiveData<StateResource<NewsResponse>>()
    val search : LiveData<StateResource<NewsResponse>> get() = _search

    fun fetchSearch(query: String) = viewModelScope.launch {
        safeFetchSearch(query)
    }

    private fun safeFetchSearch(query: String) = viewModelScope.launch{
        _search.value = StateResource.Loading()
        try {
            val response = repository.search(query)
            _search.value = handleResponse(response)
        } catch (e: Exception){
            _search.value = StateResource.Error("Artigo não encontrado: ${e.message}")
        }
    }

    private fun handleResponse(response: Response<NewsResponse>): StateResource<NewsResponse>? {
        if(response.isSuccessful){
            response.body()?.let { values ->
                return StateResource.Success(values)
            }
        }
        return StateResource.Error(response.message())
    }
}