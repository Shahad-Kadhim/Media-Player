package com.example.mediaplayer.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mediaplayer.ui.BaseInterface

abstract class BaseFragment<VB:ViewBinding>:Fragment(),BaseInterface<VB> {
  override var _binding: ViewBinding?=null
    override var binding: VB?
        get() = _binding as VB?
        set(value) = TODO()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        callBack()
    }

    @SuppressLint("ResourceType")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) :View? {
        _binding=bindingInflater(layoutInflater)
        return _binding?.root
    }

}