package com.barak.rx.utils

import io.reactivex.android.MainThreadDisposable
import android.support.v7.widget.RecyclerView
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe


/**
 * Created by Barak on 2016.07.29..
 */
class RecyclerViewScrollBottomOnSubscribe(val view: RecyclerView) : ObservableOnSubscribe<Boolean> {
    override fun subscribe(emitter: ObservableEmitter<Boolean>) {
        MainThreadDisposable.verifyMainThread()

        val watcher = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1)) {
                    onScrolledToBottom()
                }
            }

            fun onScrolledToBottom() {
                if (!emitter.isDisposed) {
                    emitter.onNext(true)
                }
            }
        }

        emitter.setDisposable(object : MainThreadDisposable() {
            override fun onDispose() {
                view.removeOnScrollListener(watcher)
            }
        })

        view.addOnScrollListener(watcher)

        // Emit initial value.
        emitter.onNext(false)
    }
}
