package com.siddhant.news.model.data

data class BaseModel<T>(
    val status: String?,
    val message: String?,
    val articles: T
)
