package com.siddhant.news.dagger

import android.app.Application
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.siddhant.news.room.NewsDB
import com.siddhant.news.room.NewsDao
import com.siddhant.news.util.CommonUtil
import dagger.Module


@Module(includes = [AppModule::class])
class DBModule(val mApplication: Application) {

    private val newsDB: NewsDB =
        Room.databaseBuilder(mApplication, NewsDB::class.java, CommonUtil.DB_NAME).build()

    @Singleton
    @Provides
    internal fun providesNewsDatabase(): NewsDB {
        return newsDB
    }


    @Singleton
    @Provides
    internal fun providesNewsDao(newsDB: NewsDB): NewsDao {
        return newsDB.newsDao()
    }
}