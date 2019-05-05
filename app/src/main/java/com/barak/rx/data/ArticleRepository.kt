package com.barak.rx.data

import android.util.Log
import com.barak.rx.data.local.dao.ArticleDao
import com.barak.rx.data.remote.api.Article
import com.barak.rx.data.remote.task.ArticleTaskFactory
import com.barak.rx.data.remote.task.ArticleTaskManager
import com.barak.rx.utils.schedulers.Scheduler
import io.reactivex.Flowable
import io.reactivex.rxkotlin.subscribeBy

class ArticleRepository(
        val catDao: ArticleDao,
        val catTaskManager: ArticleTaskManager,
        val catTaskFactory: ArticleTaskFactory,
        val mainScheduler: Scheduler
) {
    companion object {
        const val TAG = "ArticleRepository"
    }

    fun startListeningForCats(): Flowable<List<Article>> =
        catDao.listenForArticles()
            .distinctUntilChanged()
            .observeOn(mainScheduler.asRxScheduler())
            .replay(1)
            .autoConnect(0)


    private fun isDownloadInProgress(): Boolean =
        catTaskManager.isRegistered(ArticleTaskManager.SAVE_CATS_TASK)

    fun startFetch(scrolled: Boolean) {
        if(!isDownloadInProgress()) {
            catTaskFactory.createSaveCatsTask().subscribeBy(onSuccess = {
                // Success
            }, onError = { throwable ->
                Log.i(TAG, "Fetching failed", throwable)
            })
        }
    }
}