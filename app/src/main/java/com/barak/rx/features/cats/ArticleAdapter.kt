package com.barak.rx.features.cats

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.barak.rx.R
import com.barak.rx.application.GlideApp
import com.barak.rx.data.remote.api.Article
import com.barak.rx.utils.inflate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_cat_item.*
import org.jetbrains.anko.sdk15.listeners.onClick
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(val catViewModel: ArticleViewModel) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    @SuppressLint("SimpleDateFormat")
    internal var sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    @SuppressLint("SimpleDateFormat")
    internal var SimpleDateFormatUI = SimpleDateFormat("EEE, d MMM yyyy HH:mm")
    init {
        sdf.timeZone = TimeZone.getTimeZone("IL")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(parent.inflate(R.layout.view_cat_item))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }

    private var list: List<Article> = Collections.emptyList()

    fun updateData(articles: List<Article>?) {
        if (articles!!.isNotEmpty()){
            this.list = articles
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(article: Article) {
            title_text.text = article.title
            try {
                val date = sdf.parse(article.publishedAt)
                date_text.text = SimpleDateFormatUI.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            GlideApp.with(containerView)
                    .load(article.urlToImage).diskCacheStrategy(DiskCacheStrategy.ALL).into(img_view)
            articleItemContainer.onClick { view ->
                catViewModel.openArticlePage(article)
            }
        }
    }
}