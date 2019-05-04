package com.zhuinden.mvvmaacrxjavaretrofitroom.application

import android.app.Application
import android.arch.persistence.room.Room
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.ArticleRepository
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.dao.ArticleDao
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.local.database.DatabaseManager
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.service.ArticleService
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task.ArticleTaskFactory
import com.zhuinden.mvvmaacrxjavaretrofitroom.data.remote.task.ArticleTaskManager
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.IoScheduler
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.MainScheduler
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.NetworkScheduler
import com.zhuinden.mvvmaacrxjavaretrofitroom.utils.schedulers.Scheduler
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CustomApplication : Application() {
    private lateinit var databaseManager: DatabaseManager

    lateinit var articleDao: ArticleDao
        private set

    lateinit var retrofit: Retrofit
        private set

    lateinit var articleService: ArticleService
        private set

    lateinit var mainScheduler: Scheduler
        private set

    lateinit var networkScheduler: Scheduler
        private set

    lateinit var ioScheduler: Scheduler
        private set

    lateinit var articleTaskFactory: ArticleTaskFactory
        private set

    lateinit var catTaskManager: ArticleTaskManager
        private set

    lateinit var catRepository: ArticleRepository
        private set

    override fun onCreate() {
        super.onCreate()
        databaseManager = Room.databaseBuilder(this, DatabaseManager::class.java, "database")
            .fallbackToDestructiveMigration() //
            .build();

        articleDao = databaseManager.articleDao
        retrofit = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org/v2/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

//                .baseUrl("http://thecatapi.com/")
        articleService = retrofit.create(ArticleService::class.java);

        mainScheduler = MainScheduler()
        ioScheduler = IoScheduler()
        networkScheduler = NetworkScheduler()

        catTaskManager = ArticleTaskManager()
        articleTaskFactory = ArticleTaskFactory(catTaskManager, articleService, articleDao)

        catRepository = ArticleRepository(articleDao, catTaskManager, articleTaskFactory, mainScheduler)
    }
}
