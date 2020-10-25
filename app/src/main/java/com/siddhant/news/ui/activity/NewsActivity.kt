package com.siddhant.news.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.siddhant.news.ClickListener
import com.siddhant.news.R
import com.siddhant.news.adapter.NewsAdapter
import com.siddhant.news.model.data.NewsData
import com.siddhant.news.util.CommonUtil
import com.siddhant.news.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_main.*


class NewsActivity : BaseActivity()
    , ClickListener {

    var countryParam: String? = "us"
    var sortParam: String? = null

    override val layoutResourceId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
        setToolbarText("Top News")
        hideBack()
        getNewsApi()
    }

    private fun init() {
        tvLocation.visibility = View.VISIBLE
        ivLocation.setImageResource(R.drawable.ic_location_pin)
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.sort,
            android.R.layout.simple_spinner_item
        ).apply {
            setDropDownViewResource(R.layout.spinneritem)
        }

        spinnerSort.adapter = spinnerAdapter
        spinnerSort.onItemSelectedListener = object :
            OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    (0) -> {
                        sortParam = "publishedAt"
                    }
                    (1) -> {
                        sortParam = "popularity"
                    }
                    (2) -> {
                        sortParam = "publishedAt"
                    }
                }
                getNewsApi(sortParam.orEmpty(), countryParam.orEmpty())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        svNews.isSubmitButtonEnabled = true
        svNews.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                getNewsApi(sortParam.orEmpty(), countryParam.orEmpty(), p0)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {

                if (query != null && query.length > 2) {
                    getNewsApi(sortParam.orEmpty(), countryParam.orEmpty(), query)
                }
                return true
            }
        })

        svNews.setOnCloseListener {

            getNewsApi(sortParam.orEmpty(), countryParam.orEmpty())
            return@setOnCloseListener true
        }

        tvLocation.setOnClickListener {

            val addBottomFragment = BottomSheetLayoutFragment.newInstance()
            addBottomFragment?.show(supportFragmentManager, "add_bottom_sheet_opener")
        }

    }


    private fun getNewsApi(
        sortBy: String = "publishedAt",
        country: String = "us",
        search: String? = null
    ) {
        val newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        showProgress()
        newsViewModel.getNewsLiveData(country, sortBy, search)
            ?.observe(this, Observer { newsData ->
                hideProgress()
                showData()
                when {
                    newsData.status.equals("ok", true) -> {
                        if (newsData.articles.size > 0) {
                            setAdapter(newsData.articles)
                            rvNews.visibility = View.VISIBLE
                            tvNoData.visibility = View.GONE
                        } else {
                            rvNews.visibility = View.GONE
                            tvNoData.visibility = View.VISIBLE
                        }
                    }
                    newsData.status.equals(CommonUtil.NO_INTERNET, true) -> {
                        Toast.makeText(this@NewsActivity, "No Internet", Toast.LENGTH_SHORT).show()
                    }
                    newsData.status.equals(CommonUtil.API_FAILURE, true) -> {
                        Toast.makeText(
                            this@NewsActivity,
                            "Some Error Occurred.Please Try Again Later",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
    }

    private fun setAdapter(newsList: ArrayList<NewsData>) {
        rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            adapter = NewsAdapter(this@NewsActivity, newsList)
            (adapter as NewsAdapter).onItemClick = { apiresult ->
                val i = Intent(this@NewsActivity, NewsDescriptionActivity::class.java)
                i.putExtra(CommonUtil.KEY_NEWS_URL, apiresult[0])
                i.putExtra(CommonUtil.KEY_NEWS_IMAGE, apiresult[1])
                i.putExtra(CommonUtil.KEY_NEWS_PUBLISHEDAT, apiresult[2])
                i.putExtra(CommonUtil.KEY_NEWS_TITLE, apiresult[3])
                i.putExtra(CommonUtil.KEY_NEWS_CONTENT, apiresult[4])
                startActivity(i)
            }
        }
    }

    override fun onApplyButtonClicked(countryParam: String, countryName: String) {
        this.countryParam = countryParam
        getNewsApi(sortParam.orEmpty(), countryParam)
        tvLocation.text = countryName
    }

}
