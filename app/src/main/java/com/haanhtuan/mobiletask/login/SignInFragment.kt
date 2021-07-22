package com.haanhtuan.mobiletask.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.haanhtuan.mobiletask.R
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.databinding.FragmentSigninBinding
import com.haanhtuan.mobiletask.utils.Utils

class SignInFragment : BaseFragment(), View.OnClickListener {
    private var signInViewModel: LoginViewModel? = null
    private lateinit var binding: FragmentSigninBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentSigninBinding.inflate(layoutInflater)
        signInViewModel?.initFireBaseAuth()

        binding.signInBtn.setOnClickListener(this)
        binding.signUpBtn.setOnClickListener(this)
        setUpShowHideLoading()
        signInViewModel?.autologinIfNeeded()
        showSignInErrorDialogIfNeeded()

        return binding.root
    }

    private fun setUpShowHideLoading() {
        signInViewModel?.showLoading?.observe(viewLifecycleOwner, {
            if (it) {
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = activity?.let { ViewModelProvider(it).get(LoginViewModel::class.java) }
        signInViewModel?.initFireBaseAuth()

    }

    override fun onStart() {
        super.onStart()
        signInViewModel?.startAuth()

    }

    override fun onClick(v: View?) {

        when (v) {
            binding.signInBtn -> {
                hideKeyboard()
                signInViewModel?.signInWithUserWithEmailAndPassword(
                    binding.editTextTextPersonName.text?.toString(),
                    binding.editTextTextPassword.text?.toString()
                )
            }
            binding.signUpBtn -> {
                goToScreen(SignUpFragment())
            }
        }
    }


    private fun showSignInErrorDialogIfNeeded() {
        signInViewModel?.errorSignInDialog?.observe(viewLifecycleOwner, {
            context?.let { it1 -> Utils.errorSignInDialog(it1) }
        })
    }
}