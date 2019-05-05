package com.barak.rx.features.cats

import android.arch.lifecycle.ViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.barak.rx.data.ArticleRepository
import com.barak.rx.data.remote.api.Article
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class ArticleViewModel(
        private val articleRepository: ArticleRepository
) : ViewModel() {
    private val cats: Flowable<List<Article>> = articleRepository.startListeningForArticles()

    val openArticleEvent = PublishRelay.create<Article>()!!

    fun observeArticles(onNext: (List<Article>) -> Unit, onError: (Throwable) -> Unit = {}): Disposable =
        cats.subscribeBy(onNext = onNext, onError = onError)

    fun handleScrollToBottom(scrolled: Boolean) {
        articleRepository.startFetch(scrolled)
    }

    fun openArticlePage(article: Article) {
        openArticleEvent.accept(article)
    }
}