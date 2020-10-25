package com.siddhant.news.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.siddhant.news.model.data.NewsData

@Database(entities = [NewsData::class], version = 1, exportSchema = false)
abstract class NewsDB : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}