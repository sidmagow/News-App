package com.siddhant.news.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.siddhant.news.repository.NewsRepository
import com.siddhant.news.AppController
import com.siddhant.news.model.data.BaseModel
import com.siddhant.news.model.data.NewsData
import javax.inject.Inject


class NewsViewModel(application: Application) : AndroidViewModel(application) {

    init {
        AppController.get(application).mApiComponent?.inject(this)
    }

    @Inject
    lateinit var mNewsRepository: NewsRepository

    fun getNewsLiveData(
        country: String,
        sortBy: String,
        search: String?
    ): LiveData<BaseModel<ArrayList<NewsData>>>? {
        return mNewsRepository?.getNews(country, sortBy, search)
    }
}