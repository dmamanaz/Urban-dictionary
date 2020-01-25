package com.example.nikeurbandictionary.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeurbandictionary.R
import com.example.nikeurbandictionary.model.Definition
import com.example.nikeurbandictionary.model.UrbanResponse
import kotlinx.android.synthetic.main.urban_list.view.*

class UrbanDictionaryAdapter (val resultList: UrbanResponse):
    RecyclerView.Adapter<UrbanDictionaryAdapter.UrbanDictionaryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UrbanDictionaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.urban_list, parent, false))

    override fun getItemCount()= resultList.list.size

    override fun onBindViewHolder(holder: UrbanDictionaryViewHolder, position: Int) {
        holder.bindItem(resultList.list[position])
    }


    class UrbanDictionaryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(resultList: Definition) {
            itemView.definition_tv.text = resultList.definition
            itemView.thumbsUp_tv.text = resultList.thumbs_up.toString()
            itemView.thumbsDown_tv.text = resultList.thumbs_down.toString()
        }
    }
}