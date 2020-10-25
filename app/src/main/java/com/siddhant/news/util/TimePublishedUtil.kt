package com.siddhant.news.util

class TimePublishedUtil {

    companion object{
        fun getTimePublished(publishedAt: String): Map<String, String> {
            val date = publishedAt.substring(0, publishedAt.indexOf('T') )
            val time: String =
                publishedAt.substring(publishedAt.indexOf('T') +1, publishedAt.indexOf('Z')-3)
            val dateTime = HashMap<String, String>()
            dateTime["dateOutput"] = date
            dateTime["timeOutput"] = time

            return dateTime
        }
    }
}