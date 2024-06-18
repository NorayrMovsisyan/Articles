package com.example.articlesearch.di

import com.example.articlesearch.presentation.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {
    viewModel { ArticleViewModel(get()) }
}