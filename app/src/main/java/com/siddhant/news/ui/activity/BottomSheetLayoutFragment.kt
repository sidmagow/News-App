package com.siddhant.news.ui.activity


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.siddhant.news.ClickListener
import com.siddhant.news.FragmentCalback
import com.siddhant.news.R
import com.siddhant.news.adapter.BottomSheetAdapter
import com.siddhant.news.model.data.CountryCodeData
import kotlinx.android.synthetic.main.bottomsheet_layout.*


class BottomSheetLayoutFragment : BottomSheetDialogFragment(), FragmentCalback {

    private var listener: ClickListener? = null
    private var countryParam: String = "us"
    private var countryName: String = "USA"

    companion object {
        fun newInstance(): BottomSheetLayoutFragment? {
            return BottomSheetLayoutFragment()
        }
    }

    @Nullable
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.bottomsheet_layout, container,
            false
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as ClickListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationList = arrayListOf<CountryCodeData>(
            CountryCodeData("USA", "us", false),
            CountryCodeData("India", "in", false),
            CountryCodeData("Australia", "au", false)
        )
        rvBottomSheetOpener.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = BottomSheetAdapter(locationList, this@BottomSheetLayoutFragment)
        }
        closeBtn.setOnClickListener {
            listener?.onApplyButtonClicked(countryParam, countryName)
            dismiss()
        }
    }


    override fun onRadioButtonSelected(
        countryParam: String,
        countryName: String
    ) {
        this.countryParam = countryParam
        this.countryName = countryName
    }
}

