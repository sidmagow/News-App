package com.siddhant.news;

import android.app.Application;
import com.siddhant.news.dagger.*


class AppController : Application() {
    companion object {
        private var mInstance: AppController? = null
        fun get(application: Application): AppController {
            return application as AppController
        }
    }

    var mApiComponent: ApiComponent? = null

    override fun onCreate() {
        super.onCreate()
        super.onCreate();
        mInstance = this
        setDaggerComponent()
    }

    fun setDaggerComponent() {
        mApiComponent = DaggerApiComponent.builder()
            .appModule(AppModule(this))
            .dBModule(DBModule(this))
            .apiHelper(ApiHelper())
            .build()
    }
}
