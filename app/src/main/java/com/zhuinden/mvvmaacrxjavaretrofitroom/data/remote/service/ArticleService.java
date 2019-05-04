package com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.service;

import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.api.ArticleResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("everything?sortBy=publishedAt&apiKey=20db1cfcc97442bcb409ee97e3360cd9")
    Observable<ArticleResponse> getUser(@Query("q") String city, @Query("apiKey") String apiKey);

//
//    @GET("everything?q=bitcoin&from=2019-04-02&sortBy=publishedAt&apiKey=20db1cfcc97442bcb409ee97e3360cd9{otp}")
//    Observable<ArticleResponse> getUser();
}

