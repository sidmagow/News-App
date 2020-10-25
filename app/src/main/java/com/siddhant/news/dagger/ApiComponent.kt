package com.siddhant.news.dagger

import com.siddhant.news.api.NewsApi
import com.siddhant.news.repository.NewsRepository
import com.siddhant.news.viewmodel.NewsViewModel
import javax.inject.Singleton
import dagger.Component

@Singleton
@Component(modules = [ApiHelper::class, AppModule::class, DBModule::class])
interface ApiComponent {

    fun getNewsApi(): NewsApi
    fun inject(newsVM: NewsViewModel)
    fun inject(repo: NewsRepository)

}
