package com.siddhant.news.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.siddhant.news.model.data.NewsData

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    @JvmSuppressWildcards
    fun insertNews(news: NewsData?): Long

    @Query("SELECT * FROM `NewsData`")
    fun getNews(): List<NewsData>

}