package com.example.mediaplayer.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

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