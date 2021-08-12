package com.example.mediaplayer.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.ItemFilmBinding
import com.example.mediaplayer.response.Item

class RecycleAdapter(private var films: List<Item>, private val listener: FilmInteractionListener) :
    RecyclerView.Adapter<RecycleAdapter.ItemHolderView>() {

    class ItemHolderView(view: View) : RecyclerView.ViewHolder(view) {
        var binding= ItemFilmBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ItemHolderView(
            LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
        )

    override fun onBindViewHolder(holder: ItemHolderView, position: Int) {
        val s =films[position]
        holder.binding.apply {
            filmTitle.text=s.title
            filmDuration.text="${s.duration/60} min"
            filmDescription.text=s.description
            filmYear.text=(s.year).toString()
            if ( s.ratings != null) {
                filmRank.visibility=View.VISIBLE
                filmRank.text=s.ratings.toString()
            }else{
                filmRank.visibility=View.INVISIBLE
            }
            Glide.with(holder.itemView.context).load(s.art).into(filmImage)
            root.setOnClickListener { listener.onClickItem(s) }
        }
    }
    override fun getItemCount()=films.size

    fun setData(newList:List<Item>){
        films=newList
        notifyDataSetChanged()
    }
}