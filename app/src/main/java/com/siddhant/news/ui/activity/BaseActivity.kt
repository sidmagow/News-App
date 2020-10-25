package com.siddhant.news.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.siddhant.news.R
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.error_layout.view.*


abstract class BaseActivity : AppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutResourceId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        setSupportActionBar(toolbar)
        LayoutInflater.from(this).inflate(layoutResourceId, layoutContainer, true)
        ivBack.setOnClickListener { onBackPressed() }
        included_error_layout.tvErrorRefresh.setOnClickListener { errorRefreshClicked() }
    }

    private fun errorRefreshClicked() {}

    fun showToast(msg: String) {
        if (TextUtils.isEmpty(msg)) {
            return
        }
        Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT).show()
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun showOverlayProgress() {
        overlayProgressContainer.visibility = View.VISIBLE
    }

    fun hideOverlayProgress() {
        overlayProgressContainer.visibility = View.GONE
    }

    fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun showData() {
        layoutContainer.visibility = View.VISIBLE
    }

    fun hideData() {
        layoutContainer.visibility = View.GONE
    }

    fun showError() {
        layoutError.visibility = View.VISIBLE
    }

    fun hideError() {
        layoutError.visibility = View.GONE
    }

    fun hideBack() {
        ivBack.visibility = View.GONE
    }

    fun getData() {
        layoutContainer.visibility = View.VISIBLE
    }

    fun setToolbarText(toolbarText: String) {
        tvToolbarTitle.text = toolbarText
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        if (!isVisible)
            toolbar.visibility = View.GONE
    }

}