package com.example.mediaplayer.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.FragmentHomeBinding
import com.example.mediaplayer.model.response.Item
import com.example.mediaplayer.model.response.Data
import com.example.mediaplayer.model.Status
import com.example.mediaplayer.presenter.HomePresenter
import com.example.mediaplayer.ui.adapters.FilmInteractionListener
import com.example.mediaplayer.ui.adapters.RecycleAdapter
import com.example.mediaplayer.ui.adapters.SliderAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(), FilmInteractionListener, IHomeView {
    override val TAG="HOME_Fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding=FragmentHomeBinding::inflate
    private lateinit var mAdapter:RecycleAdapter
    lateinit var mresponse:Status.Success<Data>
    val presenter= HomePresenter(this)

    @InternalCoroutinesApi
    override fun setup() {
        presenter.getFilm()
    }

    private fun onFilmsResponse(response: Status<Data>) {
        binding?.apply {
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
            binding?.apply {
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
        mresponse.data.feed.all {
            this.addAll(it.items)
        }
    }

    override fun callBack() {
        binding?.apply {
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
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.fragment_container
                , PLayerFragment().apply {
                    arguments= Bundle().apply {
                        putParcelable("VideoURL", film)
                        }
                })?.addToBackStack("")?.commit()
    }

    private fun hideAllViews(){
        binding?.run {
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


    override fun onGetFilms(flow: Flow<Status<Data>>) {
        activity?.lifecycleScope?.launch {
            flow.catch {
                Toast.makeText(activity, "check internet connection", Toast.LENGTH_SHORT).show()
            }.collect {
                onFilmsResponse(it)
            }
        }
    }
}