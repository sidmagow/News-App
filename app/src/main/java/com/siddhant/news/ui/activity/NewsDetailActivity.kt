package com.siddhant.news.ui.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.siddhant.news.R
import kotlinx.android.synthetic.main.activity_news_detail.*

class NewsDetailActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() {
            return R.layout.activity_news_detail
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideProgress()
        showData()
        setToolbarText("News Details")
        val url: String = intent.getStringExtra("KEY_NEWS_URL_1");
        wvNews.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        wvNews.settings.javaScriptEnabled = true;
        wvNews.loadUrl(url)
    }
}