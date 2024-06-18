package com.example.articlesearch.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.articlesearch.data.local.ArticleDataBase
import com.example.articlesearch.data.local.ArticleEntity
import com.example.articlesearch.data.mapper.toArticle
import com.example.articlesearch.data.remot.ArticleApi
import com.example.articlesearch.data.remot.ArticleRemoteMediator
import com.example.articlesearch.domain.ArticleRepository
import com.example.articlesearch.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class ArticleRepositoryImpl(
    private val articleDataBase: ArticleDataBase,
    private val articleApi: ArticleApi
) : ArticleRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getArticlesFlow(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ArticleRemoteMediator(
                articleDataBase = articleDataBase,
                articleApi = articleApi,
                query = query
            ),
            pagingSourceFactory = {
                articleDataBase.dao.pagingSource()
            }
        ).flow
            .map { pagingData -> pagingData.map { it.toArticle() } }
            .flowOn(Dispatchers.IO)
    }
}