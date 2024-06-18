package com.example.articlesearch.domain

import androidx.paging.PagingData
import com.example.articlesearch.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface ArticleRepository {
    fun getArticlesFlow(query: String): Flow<PagingData<Article>>
}