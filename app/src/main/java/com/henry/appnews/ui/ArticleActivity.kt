package com.henry.appnews.ui

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.henry.appnews.R
import com.henry.appnews.databinding.ActivityArticleBinding
import com.henry.appnews.model.Article
import com.henry.appnews.model.data.NewsDataSource
import com.henry.appnews.presenter.ViewHome
import com.henry.appnews.presenter.favorite.FavoritePresenter

class ArticleActivity : AppCompatActivity(), ViewHome.Favorite {

    private lateinit var article: Article
    private lateinit var presenter: FavoritePresenter
    private lateinit var binding: ActivityArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getArticle()

        val dataSource = NewsDataSource(this)
        presenter = FavoritePresenter(this, dataSource)


        with(binding.webView) {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }

        binding.fab.setOnClickListener {
            presenter.saveArticle(article)
            Snackbar.make(
                it, R.string.article_saved_successful,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    private fun getArticle() {
        intent.extras?.let {
            article = it.get("article") as Article
        }
    }

    override fun showArticles(articles: List<Article>) {
    }
}