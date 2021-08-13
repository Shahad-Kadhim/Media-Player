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
import com.example.mediaplayer.ui.FilmInteractionListener
import com.example.mediaplayer.ui.RecycleAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment<FragmentHomeBinding>(), FilmInteractionListener, IHomeView {
    override val TAG="HOME_Fragment"
    override val bindingInflater: (LayoutInflater) -> FragmentHomeBinding=FragmentHomeBinding::inflate

    val presenter= HomePresenter(this)

    @InternalCoroutinesApi
    override fun setup() {
        presenter.getFilm()
    }

    fun onFilmsResponse(response: Status<Data>) {
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
                    recycle.apply {
                        show()
                        adapter = RecycleAdapter(
                            mutableListOf<Item>().apply {
                                response.data.feed.all { this.addAll(it.items) }
                            },
                            this@HomeFragment)
                    }
                }
            }
        }
    }
    override fun callBack() {
    }

    override fun onClickItem(url:String) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.add(R.id.fragment_container
                , PLayerFragment().apply {
                    arguments= Bundle().apply {
                        putString("VideoURL", url)}
                })?.addToBackStack("")?.commit()
    }

    private fun hideAllViews(){
        binding?.run {
            progressBar.hide()
            imageError.hide()
            recycle.hide()
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