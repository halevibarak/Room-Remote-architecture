package com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.api

import com.google.gson.annotations.SerializedName




class ArticleResponse(@field:SerializedName("articles")
                      var articles: ArrayList<Article>)

