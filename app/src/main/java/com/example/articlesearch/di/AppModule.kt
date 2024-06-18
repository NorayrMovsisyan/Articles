package com.example.articlesearch.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.articlesearch.BuildConfig
import com.example.articlesearch.data.local.ArticleDataBase
import com.example.articlesearch.data.local.ArticleEntity
import com.example.articlesearch.data.remot.ArticleApi
import com.example.articlesearch.data.remot.ArticleRemoteMediator
import com.example.articlesearch.data.repo.ArticleRepositoryImpl
import com.example.articlesearch.domain.ArticleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

val AppModule = module {
    single { provideArticleDatabase(androidContext()) }
    single { provideArticleApi() }
    single { provideArticleRepository(get(), get()) }
}

fun provideArticleDatabase(context: Context): ArticleDataBase {
    return Room.databaseBuilder(
        context,
        ArticleDataBase::class.java,
        "Articles.db"
    ).build()
}

fun provideArticleApi(): ArticleApi {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()
}

fun provideArticleRepository(
    articleDataBase: ArticleDataBase,
    articleApi: ArticleApi
): ArticleRepository {
    return ArticleRepositoryImpl(articleDataBase, articleApi)
}