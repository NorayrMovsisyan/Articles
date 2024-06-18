package com.example.articlesearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ArticleEntity::class],
    version = 1
)
abstract class ArticleDataBase:RoomDatabase() {
    abstract val dao: ArticleDao
}