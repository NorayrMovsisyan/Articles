package com.example.articlesearch.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.articlesearch.domain.ArticleRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val articleRepository: ArticleRepository
): ViewModel() {

    val searchText = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val articles =
        searchText.flatMapLatest {
            articleRepository.getArticlesFlow(it)
                .cachedIn(viewModelScope)
                .catch { throwable -> Log.e("ArticleViewModel", "Error fetching articles", throwable) }
        }



    fun search(query: String) = viewModelScope.launch {
        searchText.emit(query)
    }
}