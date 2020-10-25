package com.siddhant.news.model.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName


@Entity(primaryKeys = ["title"])
data class NewsData(
    val title: String,
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?,
    val url: String?
)