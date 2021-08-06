package com.example.mediaplayer.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mediaplayer.databinding.ActivityMainBinding
import com.example.mediaplayer.network.Client
import com.example.mediaplayer.response.Item
import com.example.mediaplayer.response.data
import com.example.mediaplayer.util.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<ActivityMainBinding>(),FilmInteractionListener {
    override val TAG="MAIN_ACTIVITY"
    override val inflater: (LayoutInflater) -> ActivityMainBinding=ActivityMainBinding::inflate
    @InternalCoroutinesApi
    override fun setup() {
        lifecycleScope.launch {
         flow {
             emit(Status.Loading)
             emit(Client.getFilms())
         }.flowOn(Dispatchers.IO).catch {
             Toast.makeText(this@MainActivity, "check internet connection", Toast.LENGTH_SHORT).show()
         }.collect { onFilmsResponse(it) }
        }
    }
fun onFilmsResponse(response: Status<data>) {
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
                recycle.apply {
                    show()
                    adapter = RecycleAdapter(
                        mutableListOf<Item>().apply {
                            response.data.feed.all { this.addAll(it.items) }
                        },
                        this@MainActivity)
                }
            }
        }
    }
}
    override fun callback() {
    }

    override fun onClickItem(url:String) {
        val intent=Intent(this,PLayer::class.java)
        intent.putExtra("VideoURL",url)
        startActivity(intent)
    }
    private fun hideAllViews(){
        binding.run {
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
}