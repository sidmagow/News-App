package com.siddhant.news.api


import com.siddhant.news.model.data.BaseModel
import com.siddhant.news.model.data.NewsData
import retrofit2.Call
import retrofit2.http.*

interface NewsApi {

    @GET("/v2/top-headlines")
    fun getNews(
        @Query("apiKey") apiKey: String?, @Query("country") country: String?,
        @Query("sortBy") sortValue: String?, @Query("q") search: String? = null
    ): Call<BaseModel<ArrayList<NewsData>>>?
}