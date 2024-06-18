package com.example.articlesearch.data.remot

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.articlesearch.data.local.ArticleDataBase
import com.example.articlesearch.data.local.ArticleEntity
import com.example.articlesearch.data.mapper.toArticleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator(
    private val articleDataBase: ArticleDataBase,
    private val articleApi: ArticleApi,
    private val query: String
): RemoteMediator<Int, ArticleEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ArticleEntity>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if(lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val articles = articleApi.getArticles(
                page = loadKey,
                pageSize = state.config.pageSize,
                query = query
            )

            articleDataBase.withTransaction {
                if(loadType == LoadType.REFRESH) {
                    articleDataBase.dao.clearAll()
                }
                val articleEntity = articles.response.results.map { it.toArticleEntity() }
                articleDataBase.dao.upsertAll(articleEntity)
            }

            MediatorResult.Success(
                endOfPaginationReached = articles.response.results.isEmpty()
            )
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}