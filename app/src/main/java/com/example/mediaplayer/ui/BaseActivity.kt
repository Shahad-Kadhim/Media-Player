package com.example.mediaplayer.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.mediaplayer.R
import com.example.mediaplayer.databinding.ActivityMainBinding

abstract class BaseActivity<VB:ViewBinding> :AppCompatActivity(){
    abstract  val TAG: String
    lateinit  var binding: VB
    abstract val inflater : (LayoutInflater)->VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=inflater(layoutInflater)
        setContentView(binding.root)
        setup()
        callback()
    }
    abstract fun setup()
    abstract fun callback()
}