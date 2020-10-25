package com.siddhant.news.util

import com.siddhant.news.BuildConfig
import com.siddhant.news.R


class CommonUtil {
    companion object {
        const val API_FAILURE = "API_FAILURE"
        const val PLACEHOLDER_NEWS = R.drawable.image_background;
        const val NEWS_API_KEY = "b24e441ac0f24c15875947f2afb75a05";
        const val KEY_NEWS_URL = "news_url";
        const val KEY_NEWS_TITLE = "news_title"
        const val KEY_NEWS_IMAGE = "news_image"
        const val KEY_NEWS_CONTENT = "news_content"
        const val KEY_NEWS_PUBLISHEDAT = "news_time"
        const val BASE_URL = BuildConfig.BASE_URL + "/api/"
        const val API_VERSION = "v1"
        const val DB_NAME = "TopNews.db"
        const val NO_INTERNET = "NO_INTERNET"

    }
}