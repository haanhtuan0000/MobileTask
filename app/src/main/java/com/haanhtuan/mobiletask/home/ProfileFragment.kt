package com.haanhtuan.mobiletask.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.R
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.databinding.FragmentProfileBinding
import com.haanhtuan.mobiletask.login.SignInFragment

class ProfileFragment : BaseFragment(), View.OnClickListener {
    private lateinit var binding: FragmentProfileBinding
    private var homeViewModel: HomeViewModel? = null

    override fun onClick(v: View?) {
        when (v) {
            binding.logOutBtn -> {
                homeViewModel?.signOutFirebase()
                removeAllFragmentInBackStack()
                goToScreen(SignInFragment())
            }
            binding.backTv -> {
                activity?.onBackPressed()
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel = activity?.let { ViewModelProvider(it).get(HomeViewModel::class.java) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.logOutBtn.setOnClickListener(this)
        binding.backTv.setOnClickListener(this)

        binding.emailTv.text = MyApplication.instance.email

        return binding.root
    }
}