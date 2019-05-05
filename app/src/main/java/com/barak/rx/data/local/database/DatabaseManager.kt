package com.barak.rx.data.local.database

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.Database
import com.barak.rx.data.local.dao.ArticleDao
import com.barak.rx.data.remote.api.Article


@Database(entities = [Article::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class DatabaseManager : RoomDatabase() {
    abstract val articleDao: ArticleDao
}