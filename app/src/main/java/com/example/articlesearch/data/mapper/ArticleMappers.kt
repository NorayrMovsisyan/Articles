package com.example.articlesearch.data.mapper

import com.example.articlesearch.data.formatDate
import com.example.articlesearch.data.local.ArticleEntity
import com.example.articlesearch.data.remot.Result
import com.example.articlesearch.domain.model.Article

fun Result.toArticleEntity(): ArticleEntity {
    return ArticleEntity(
        title = pillarName ?: "",
        date = webPublicationDate,
        imageUrl = webUrl ?: "",
        description = webTitle ?: "",
    )
}

fun ArticleEntity.toArticle(): Article {
    return Article(
        title = title,
        date = date.formatDate(),
        imageUrl = imageUrl,
        description = description,
    )
}