package com.siddhant.news.ui.activity

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.siddhant.news.R
import com.siddhant.news.util.CommonUtil
import com.siddhant.news.util.TimePublishedUtil.Companion.getTimePublished

import kotlinx.android.synthetic.main.activity_news_description.*

class NewsDescriptionActivity : BaseActivity() {

    override val layoutResourceId: Int
        get() {
            return R.layout.activity_news_description
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideProgress()
        showData()
        setToolbarText("News Details")
        val url: String = intent.getStringExtra(CommonUtil.KEY_NEWS_URL).orEmpty()
        val imagePath: String? = intent.getStringExtra(CommonUtil.KEY_NEWS_IMAGE).orEmpty()
        val title: String = intent.getStringExtra(CommonUtil.KEY_NEWS_TITLE).orEmpty()
        val publishedAt: String = intent.getStringExtra(CommonUtil.KEY_NEWS_PUBLISHEDAT).orEmpty()
        val content: String = intent.getStringExtra(CommonUtil.KEY_NEWS_CONTENT).orEmpty()
        if (imagePath != "") {

            Glide.with(this)
                .load(imagePath)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.image_background)
                        .fallback(R.drawable.image_background)
                        .error(R.drawable.image_background)
                        .centerInside()
                )
                .into(ivNewsImage)
        }

        tvNewsDescriptor.text = title
        tvNewsContent.text = content
       val dateAndTime = getTimePublished(publishedAt)
        tvNewsTimestamp.text =dateAndTime["dateOutput"].toString()+" at "+ dateAndTime["timeOutput"].toString()
        tvFullStoryLink.setOnClickListener {
            val i = Intent(this, NewsDetailActivity::class.java)
            i.putExtra("KEY_NEWS_URL_1", url)
            startActivity(i)
        }
    }
}