package com.siddhant.news.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.siddhant.news.R
import com.siddhant.news.model.data.NewsData
import com.siddhant.news.util.TimePublishedUtil.Companion.getTimePublished
import kotlinx.android.synthetic.main.item_news.view.*


class NewsAdapter(var context: Context, var newsList: ArrayList<NewsData>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    lateinit var onItemClick: ((ArrayList<String>) -> Unit?)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(itemView, onItemClick)
    }


    override fun getItemCount(): Int = newsList.size


    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setData(newsList[position])
    }


    class NewsViewHolder(itemView: View, private val onItemClick: (ArrayList<String>) -> Unit?) :
        RecyclerView.ViewHolder(itemView) {
        fun setData(data: NewsData) = with(itemView) {
            tvNewsText.text = data.description
            val dateAndTime = getTimePublished(data.publishedAt.orEmpty())
            tvNewsTime.text = dateAndTime["timeOutput"].toString()

            Glide.with(this)
                .load(data.urlToImage)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.image_background)
                        .fallback(R.drawable.image_background)
                        .error(R.drawable.image_background)
                        .centerInside()
                )
                .into(imvNewsImage)

            itemViewNews.setOnClickListener {
                var apiResponseList = arrayListOf<String>(
                    data.url.orEmpty(),
                    data.urlToImage.orEmpty(),
                    data.publishedAt.orEmpty(),
                    data.title,
                    data.description.orEmpty()
                )
                onItemClick.invoke(apiResponseList)
            }
        }



    }
}


