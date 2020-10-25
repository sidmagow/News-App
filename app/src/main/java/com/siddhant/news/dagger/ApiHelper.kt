package com.siddhant.news.dagger

import android.app.Application
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.siddhant.news.api.NewsApi
import com.siddhant.news.repository.NewsRepository
import com.siddhant.news.util.CommonUtil.Companion.API_VERSION
import com.siddhant.news.util.CommonUtil.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiHelper {

    @Provides
    @Singleton
    fun provideHttpCache(application: Application): okhttp3.Cache {
        val cacheSize: Long = 10 * 1024 * 1024
        val cache = okhttp3.Cache(application.cacheDir, cacheSize)
        return cache
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setDateFormat("yyyy-MM-dd");
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(cache: okhttp3.Cache, application: Application): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
        client.readTimeout(30, TimeUnit.SECONDS)
        client.connectTimeout(15, TimeUnit.SECONDS)
        client.writeTimeout(60, TimeUnit.SECONDS)
        client.addInterceptor(interceptor)

        client.addInterceptor {
            val ongoing = it.request().newBuilder();
            it.proceed(ongoing.build())
        }
        return client.build()
    }

    @Provides
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL + API_VERSION + "/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun getNewsApi(retroFit: Retrofit): NewsApi {
        return retroFit.create(NewsApi::class.java)
    }

    @Provides
    fun getNewsRepository(app: Application): NewsRepository {
        return NewsRepository(app)
    }


}