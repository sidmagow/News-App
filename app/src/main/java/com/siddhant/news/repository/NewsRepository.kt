package com.siddhant.news.repository


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.siddhant.news.api.NewsApi
import com.siddhant.news.AppController
import com.siddhant.news.model.data.BaseModel
import com.siddhant.news.model.data.NewsData
import com.siddhant.news.room.NewsDao
import com.siddhant.news.util.CommonUtil
import com.siddhant.news.util.NetworkUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class NewsRepository(private val mApplication: Application) {

    init {
        AppController.get(mApplication).mApiComponent?.inject(this)
    }

    private val newsLiveData: MutableLiveData<BaseModel<ArrayList<NewsData>>> = MutableLiveData()

    @Inject
    lateinit var mNewsApi: NewsApi

    @Inject
    lateinit var newsDao: NewsDao

    fun getNews(
        country: String,
        sortBy: String,
        search: String?
    ): LiveData<BaseModel<ArrayList<NewsData>>> {


        if (!NetworkUtil.isInternetAvailable(mApplication)) {
            fetchNewsOffline()
            val newsModel = BaseModel(CommonUtil.NO_INTERNET, "", ArrayList<NewsData>())
            newsLiveData.postValue(newsModel)
            return newsLiveData
        }

        val call = mNewsApi.getNews(CommonUtil.NEWS_API_KEY, country, sortBy, search)

        call?.enqueue(
            object : Callback<BaseModel<ArrayList<NewsData>>> {
                override fun onFailure(call: Call<BaseModel<ArrayList<NewsData>>>, t: Throwable) {
                    val newsModel = BaseModel(CommonUtil.API_FAILURE, "", ArrayList<NewsData>())
                    newsLiveData.postValue(newsModel)
                }

                override fun onResponse(
                    call: Call<BaseModel<ArrayList<NewsData>>>,
                    response: Response<BaseModel<ArrayList<NewsData>>>
                ) {
                    val newsModel: BaseModel<ArrayList<NewsData>> = if (response.code() == 200) {
                        insertNews(response.body()?.articles as ArrayList<NewsData>)
                        response.body()!!
                    } else {
                        BaseModel(CommonUtil.API_FAILURE, "", ArrayList<NewsData>())
                    }
                    newsLiveData.postValue(newsModel)
                }
            })
        return newsLiveData
    }


    fun insertNews(newsList: ArrayList<NewsData>?) {
        doAsync {

            var needsUpdate = false
            if (newsList != null) {
                for (item in newsList) {
                    val inserted = newsDao.insertNews(item)
                    if (inserted == -1L) {
                        val updated = newsDao.insertNews(item)
                        if (updated > 0) {
                            needsUpdate = true
                        }
                    } else {
                        needsUpdate = true
                    }
                }
            }

            uiThread {
                if (needsUpdate)
                    fetchNewsOffline()
            }
        }
    }


    private fun
            fetchNewsOffline() {
        doAsync {
            val result = newsDao.getNews()
            uiThread {
                val newsArraylist = ArrayList<NewsData>()
                newsArraylist.addAll(result)
                val dbData = BaseModel("ok", "", newsArraylist)
                newsLiveData.postValue(dbData)
            }
        }
    }

}


