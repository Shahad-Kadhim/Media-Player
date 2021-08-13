package com.example.mediaplayer.ui.activities

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.example.mediaplayer.databinding.ActivityTabBinding
import com.example.mediaplayer.ui.fragments.HomeFragment

class TabActivity : BaseActivity<ActivityTabBinding>() {
    override val TAG="TAB_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivityTabBinding= ActivityTabBinding::inflate

    override fun setup() {
        addFragment(HomeFragment())
    }

    private fun addFragment(fragment: Fragment) {

        binding?.fragmentContainer?.let { supportFragmentManager.beginTransaction().add(it.id,fragment).addToBackStack("").commit() }

    }

    override fun callBack() {}

}