package com.barak.rx.data.local.dao


import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.barak.rx.data.remote.api.Article
import io.reactivex.Flowable

@Dao
interface ArticleDao {
    @Query("SELECT * FROM ${Article.TABLE_NAME} ORDER BY ${Article.COLUMN_TIME} DESC")
    fun listenForArticles(): Flowable<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(list: List<Article>)

    @Query("SELECT COUNT(*) FROM ${Article.TABLE_NAME}")
    fun countL(): Int
}