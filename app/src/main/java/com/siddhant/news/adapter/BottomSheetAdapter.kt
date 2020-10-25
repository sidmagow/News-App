package com.siddhant.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.siddhant.news.FragmentCalback
import com.siddhant.news.R
import com.siddhant.news.model.data.CountryCodeData
import kotlinx.android.synthetic.main.listview_item.view.*

class BottomSheetAdapter(
    var listOfItems: ArrayList<CountryCodeData>,
    private val callback: FragmentCalback?
) : RecyclerView.Adapter<BottomSheetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.listview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int = listOfItems.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.radio_item.text = (listOfItems[position].name)
        holder.itemView.radio_item.setOnClickListener {
            listOfItems.forEachIndexed { index, countryCodeData ->
                    countryCodeData.isSelected = false

            }
            listOfItems.forEachIndexed { index, countryCodeData ->
                if(position==index){
                    countryCodeData.isSelected = true
                }
            }
            callback?.onRadioButtonSelected(listOfItems[position].code, listOfItems[position].name)
            holder.itemView.radio_item.isChecked = listOfItems[position].isSelected
            notifyDataSetChanged()
        }

    }
}