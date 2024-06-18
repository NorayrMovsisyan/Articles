package com.example.articlesearch

import android.app.Application
import com.example.articlesearch.di.AppModule
import com.example.articlesearch.di.ViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ArticleApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ArticleApp)
            modules(listOf(AppModule, ViewModelModule))
        }
    }
}