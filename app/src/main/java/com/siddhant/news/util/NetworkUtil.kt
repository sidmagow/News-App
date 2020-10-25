package com.siddhant.news.util


import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.siddhant.news.AppController

class NetworkUtil {

    companion object {
        fun isInternetAvailable(application: Application): Boolean {
            val connectivityManager: ConnectivityManager =
                AppController.get(application)
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            if (networkInfo != null && networkInfo.isConnected) {
                return true
            }
            return false
        }
    }
}