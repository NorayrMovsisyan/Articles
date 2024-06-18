package com.example.articlesearch.data.remot

import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("search")
    suspend fun getArticles(
        @Query("api-key") apiKey: String = "test",
        @Query("format") format: String = "json",
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int
    ): ArticleResponse
}