package com.haanhtuan.mobiletask.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.haanhtuan.mobiletask.R
import com.haanhtuan.mobiletask.Screens
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.databinding.FragmentSignupBinding
import com.haanhtuan.mobiletask.home.HomeFragment
import com.haanhtuan.mobiletask.home.WelcomeFragment
import com.haanhtuan.mobiletask.utils.Utils

class SignUpFragment : BaseFragment(), View.OnClickListener {
    private var signInViewModel: LoginViewModel? = null
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(layoutInflater)

        binding.button.setOnClickListener(this)
        binding.signInBtn.setOnClickListener(this)
        showSignUpErrorDialogIfNeeded()
        signInViewModel?.showLoading?.observe(viewLifecycleOwner, {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = activity?.let { ViewModelProvider(it).get(LoginViewModel::class.java) }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.button -> {
                hideKeyboard()
                signInViewModel?.createUserWithEmailAndPassword(
                    binding.editTextTextPersonName.text?.toString(),
                    binding.editTextTextPassword.text?.toString()
                )
            }
            binding.signInBtn -> {
                activity?.onBackPressed()
            }
        }
    }

    private fun showSignUpErrorDialogIfNeeded() {
        signInViewModel?.errorSignUpDialog?.observe(viewLifecycleOwner, {
            context?.let { it1 -> Utils.errorSignUpDialog(it1) }
        })
    }
}