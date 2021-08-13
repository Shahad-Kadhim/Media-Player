package com.example.mediaplayer.ui.activities

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mediaplayer.databinding.FragmentHomeBinding
import com.example.mediaplayer.databinding.FragmentPlayerBinding
import com.example.mediaplayer.network.Client
import com.example.mediaplayer.response.Item
import com.example.mediaplayer.response.data
import com.example.mediaplayer.ui.adapters.FilmInteractionListener
import com.example.mediaplayer.ui.adapters.RecycleAdapter
import com.example.mediaplayer.ui.adapters.SliderAdapter
import com.example.mediaplayer.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeFragment : BaseActivity<FragmentHomeBinding>(),FilmInteractionListener {
    override val TAG="MAIN_ACTIVITY"
    private lateinit var mAdapter:RecycleAdapter
    override val inflater: (LayoutInflater) -> FragmentHomeBinding=FragmentHomeBinding::inflate
    @InternalCoroutinesApi
    override fun setup() {
        lifecycleScope.launch {
         flow {
             emit(Status.Loading)
             emit(Client.getFilms())
         }.flowOn(Dispatchers.IO).catch {
             Toast.makeText(this@HomeFragment, "check internet connection", Toast.LENGTH_SHORT).show()
         }.collect { onFilmsResponse(it) }
        }
    }
    private fun onFilmsResponse(response: Status<data>) {
        binding.apply {
            hideAllViews()
            when (response) {
                is Status.Error -> {
                    imageError.show()
                }
                is Status.Loading -> {
                    progressBar.show()
                }
                is Status.Success -> {
                    mresponse=response
                    bindLayout()
                }
            }
        }
    }


    private fun bindLayout(){
        getAllFilms().let {films->
            binding.apply {
                recycle.run{
                    show()
                    mAdapter=RecycleAdapter(films.shuffled(), this@HomeFragment).apply {
                        adapter=this
                    }
                }
                sliderCard.show()
                slider.setSliderAdapter(
                        SliderAdapter(films.filter { it.ratings!=null },this@HomeFragment))
            }
        }
    }

     private fun getAllFilms()=mutableListOf<Item>().apply {
         mresponse.data.feed.all { this.addAll(it.items) }
     }


    override fun callback() {
        binding.apply {
            chipGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                     all.id-> {
                        sliderCard.show()
                        mAdapter.setData(mutableListOf<Item>().apply {
                            mresponse.data.feed.all { this.addAll(it.items) }
                        }.shuffled())
                    }
                    historical.id -> {
                        sliderCard.hide()
                        mAdapter.setData(mresponse.data.feed[0].items)
                    }
                    chaplin.id -> {
                        sliderCard.hide()
                        mAdapter.setData(mresponse.data.feed[2].items)
                    }
                    feature.id -> {
                        sliderCard.hide()
                        mAdapter.setData(mresponse.data.feed[1].items)
                    }
                }
            }
        }
    }
    override fun onClickItem(film:Item) {
        Intent(this, PlayerFragment::class.java).apply {
            putExtra("VideoURL",film.url)
            startActivity(this)
        }
    }

    private fun hideAllViews(){
        binding.run {
            progressBar.hide()
            imageError.hide()
            recycle.hide()
            sliderCard.hide()
        }
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }

    companion object{
        lateinit var mresponse:Status.Success<data>
    }
}