package com.example.articlesearch.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ArticleDao {
    @Upsert
    suspend fun upsertAll(articles: List<ArticleEntity>)

    @Query("SELECT * FROM articleentity")
    fun pagingSource(): PagingSource<Int, ArticleEntity>

    @Query("DELETE FROM articleentity")
    suspend fun clearAll()
}