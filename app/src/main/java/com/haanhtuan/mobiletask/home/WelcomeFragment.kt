package com.haanhtuan.mobiletask.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haanhtuan.mobiletask.R
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.databinding.FragmentWelcomeBinding

class WelcomeFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentWelcomeBinding

    override fun onClick(v: View?) {
        when (v) {
            binding.listBtn -> {
                goToScreen(HomeFragment())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWelcomeBinding.inflate(layoutInflater)
        binding.listBtn.setOnClickListener(this)

        return binding.root
    }
}