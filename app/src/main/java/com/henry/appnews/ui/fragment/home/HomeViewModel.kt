package com.henry.appnews.ui.fragment.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.*
import com.henry.appnews.data.local.model.NewsResponse
import com.henry.appnews.repository.NewsRepository
import com.henry.appnews.utils.checkForInterntConnection
import com.henry.appnews.utils.state.StateResource
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(
    private val repository: NewsRepository,
    context : Application
) : AndroidViewModel(context) {

    private val _getAll = MutableLiveData<StateResource<NewsResponse>>()
    val getAll : LiveData<StateResource<NewsResponse>> get() = _getAll

    init {
        fetchAll()
    }

    private fun fetchAll() = viewModelScope.launch {
        safeFetchAll()
    }

    private fun safeFetchAll() = viewModelScope.launch {
        _getAll.value = StateResource.Loading()

        try {
            if(checkForInterntConnection(getApplication())){
                val response = repository.getAllRemote()
                _getAll.value = handleResponse(response)
            } else  _getAll.value = StateResource.Error("Sem conexão da internet")

        } catch (e: Exception) {
            _getAll.value = StateResource.Error("artigos nao encontrados: ${e.message}")
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