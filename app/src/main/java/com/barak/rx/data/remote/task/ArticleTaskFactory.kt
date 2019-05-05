package com.barak.rx.data.remote.task

import android.util.Log
import com.barak.rx.data.local.dao.ArticleDao
import com.barak.rx.data.remote.api.Article
import com.barak.rx.data.remote.api.ArticleResponse
import com.barak.rx.data.remote.service.ArticleService
import com.barak.rx.data.remote.task.ArticleTaskManager.Companion.SAVE_CATS_TASK
import io.reactivex.Observer
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ArticleTaskFactory(
        val catsTaskManager: ArticleTaskManager,
        val articleService: ArticleService,
        val dao: ArticleDao
) {
    fun createSaveCatsTask(): Single<Unit> = Single.create { emitter ->
        catsTaskManager.registerTask(SAVE_CATS_TASK)


        articleService.getUser("Android","555")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .doFinally { catsTaskManager.unregisterTask(SAVE_CATS_TASK) }
                .subscribe(object : Observer<ArticleResponse> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(articleResponse: ArticleResponse) {
                        val articles = articleResponse.articles
                        if (articles == null) {
                            emitter.onSuccess(Unit)
                            return
                        }
                        articles.mapIndexed { index: Int, article: Article ->
                            with(article) {
                                Article(title, author, url, urlToImage, publishedAt)
                            }
                        }.let { alist ->
                            Log.i(SAVE_CATS_TASK, "Saving articles to database.");
                            dao.insertArticles(alist)
                        }
                        emitter.onSuccess(Unit)
                    }


                    override fun onError(e: Throwable) {
                        emitter.onError(e)
                        Log.e("eee", e.localizedMessage)
                    }

                    override fun onComplete() {

                    }
                })

    }
}