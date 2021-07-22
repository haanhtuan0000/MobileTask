package com.haanhtuan.mobiletask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.home.HomeFragment
import com.haanhtuan.mobiletask.home.WelcomeFragment
import com.haanhtuan.mobiletask.login.LoginViewModel
import com.haanhtuan.mobiletask.login.SignInFragment

class MainActivity : AppCompatActivity() {
    private var signInViewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        goToScreen(SignInFragment())

        signInViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        navigateToNextScreen()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    private fun navigateToNextScreen() {
        signInViewModel?.navigateAfterSignInSuccessfully?.observe(this, { screen ->
            removeAllFragmentInBackStack()
            when (screen) {
                Screens.HOME -> {
                    goToScreen(HomeFragment())
                }
                Screens.WELCOME -> {
                    goToScreen(WelcomeFragment())
                }
                else -> {

                }
            }

        })
    }

    private fun removeAllFragmentInBackStack() {
        var count = supportFragmentManager.backStackEntryCount ?: 0
        while (count > 0) {
            supportFragmentManager.popBackStack()
            count--
        }
    }

    private fun goToScreen(fragment: BaseFragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }

}