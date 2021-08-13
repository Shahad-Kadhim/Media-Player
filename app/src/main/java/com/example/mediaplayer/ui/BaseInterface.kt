package com.example.mediaplayer.ui

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

interface BaseInterface <VB:ViewBinding>{
    val TAG: String
    val bindingInflater: (LayoutInflater) -> VB
    var _binding: ViewBinding?
    var binding: VB?
        get() = _binding as VB?
        set(value) = TODO()
    fun setup()
    fun  callBack()
}