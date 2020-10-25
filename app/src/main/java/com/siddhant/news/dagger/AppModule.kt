package com.siddhant.news.dagger


import android.app.Application
import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
@Singleton
class AppModule(val mApplication: Application) {


    @Provides
    fun provideApplication(): Application {
        return mApplication
    }
}
