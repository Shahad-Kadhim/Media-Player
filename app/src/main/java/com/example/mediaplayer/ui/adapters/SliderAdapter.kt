package com.example.mediaplayer.ui.adapters

import android.view.View
import com.smarteist.autoimageslider.SliderViewAdapter
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.mediaplayer.R
import com.example.mediaplayer.response.Item
import com.example.mediaplayer.databinding.SliderItemBinding


class SliderAdapter(var list:List<Item>, private val listener: FilmInteractionListener) :SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    class SliderAdapterVH(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        val binding=SliderItemBinding.bind(itemView)
    }

    override fun getCount()=list.size

    override fun onCreateViewHolder(parent: ViewGroup?) =
        SliderAdapterVH(LayoutInflater.from(parent?.context).inflate(R.layout.slider_item,null))

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        viewHolder?.binding?.apply {
            viewHolder.itemView?.context?.let {
                Glide.with(it).load(list[position].art).centerCrop().into(this.image)
            }
            title.text=list[position].title
            root.setOnClickListener { listener.onClickItem(list[position]) }
        }
    }
}