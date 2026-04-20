package com.example.myfirstkmpapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfirstkmpapp.data.DevArticle
import com.example.myfirstkmpapp.data.DevRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DevViewModel(private val repository: DevRepository) : ViewModel() {
    private val _articleState = MutableStateFlow<ResultState<List<DevArticle>>>(ResultState.Loading)
    val articleState: StateFlow<ResultState<List<DevArticle>>> = _articleState.asStateFlow()

    private val _savedArticleIds = MutableStateFlow<Set<Int>>(emptySet())
    val savedArticleIds: StateFlow<Set<Int>> = _savedArticleIds.asStateFlow()

    init {
        loadTechNews()
    }

    fun loadTechNews() {
        viewModelScope.launch {
            _articleState.value = ResultState.Loading
            repository.fetchArticles()
                .onSuccess { result ->
                    _articleState.value = ResultState.Success(result)
                }
                .onFailure { exception ->
                    _articleState.value = ResultState.Error(exception.message ?: "Koneksi Bermasalah")
                }
        }
    }

    fun toggleSaveArticle(id: Int) {
        _savedArticleIds.update { currentSet ->
            if (currentSet.contains(id)) currentSet - id else currentSet + id
        }
    }
}