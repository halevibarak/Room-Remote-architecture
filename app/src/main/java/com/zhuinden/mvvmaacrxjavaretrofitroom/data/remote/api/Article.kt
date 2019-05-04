package com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.api

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.api.Article.Companion.TABLE_NAME


@Entity(tableName = TABLE_NAME)
data class Article(
        @SerializedName("title")
        var title: String,

        @SerializedName("author")
        var author: String?,

        @PrimaryKey
        @SerializedName("url")
        var url: String,

        @SerializedName("urlToImage")
        var urlToImage: String?,

        @SerializedName("publishedAt")
        var publishedAt: String)
{
        companion object {
                const val TABLE_NAME = "ARTICLE_TABLE"
                const val COLUMN_TIME = "publishedAt"
        }
}
