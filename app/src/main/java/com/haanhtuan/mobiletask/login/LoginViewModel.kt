package com.haanhtuan.mobiletask.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.Screens

class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    var showLoading = MutableLiveData(false)
    var navigateAfterSignInSuccessfully = MutableLiveData<Screens>()
    var errorSignInDialog = MutableLiveData<Boolean>()
    var errorSignUpDialog = MutableLiveData<Boolean>()


    fun initFireBaseAuth() {
        auth = Firebase.auth

    }

    fun startAuth() {
        val currentUser = auth.currentUser
    }

    fun autologinIfNeeded() {
        val email = MyApplication.instance.prefUtil?.getString("email", "")
        val password = MyApplication.instance.prefUtil?.getString("password", "")

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            signInWithUserWithEmailAndPassword(email, password)
        }
    }

    private fun navigateAfterLoginSuccessfully() {
        val alreadyLoginBefore =
            MyApplication.instance.prefUtil?.getBoolean("alreadyLoginBefore", false) ?: false
        if (alreadyLoginBefore) {
            navigateAfterSignInSuccessfully.value = Screens.HOME
        } else {
            navigateAfterSignInSuccessfully.value = Screens.WELCOME
        }
        MyApplication.instance.prefUtil?.saveBoolean("alreadyLoginBefore", true)

    }

    fun createUserWithEmailAndPassword(email: String?, password: String?) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            showLoading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    showLoading.value = false
                    when {
                        it.isSuccessful -> {
                            handlePostLogin(email, password)
                        }
                        else -> {
                            errorSignUpDialog.value = true
                        }
                    }
                }
        }
    }

    fun signInWithUserWithEmailAndPassword(email: String?, password: String?) {
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            showLoading.value = true
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    showLoading.value = false
                    when {
                        it.isSuccessful -> {
                            handlePostLogin(email, password)
                        }
                        else -> {
                            errorSignInDialog.value = true
                        }
                    }
                }
        }
    }

    private fun handlePostLogin(email: String, password: String) {
        handleSaveCredential(email, password)
        MyApplication.instance.email = email
        navigateAfterLoginSuccessfully()
    }

    private fun handleSaveCredential(email: String, password: String) {
        removeOldCredential()
        saveNewPassword(email, password)
    }

    private fun removeOldCredential() {
        MyApplication.instance.prefUtil?.remove("password")
        MyApplication.instance.prefUtil?.remove("email")
    }

    private fun saveNewPassword(email: String, password: String) {
        MyApplication.instance.prefUtil?.saveString("password", password)
        MyApplication.instance.prefUtil?.saveString("email", email)
    }
}