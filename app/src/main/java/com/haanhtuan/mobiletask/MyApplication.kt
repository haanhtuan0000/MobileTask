package com.haanhtuan.mobiletask

import android.app.Application
import android.content.SharedPreferences
import com.haanhtuan.mobiletask.utils.SharePreferenceUtils

class MyApplication : Application() {
    companion object {
        @get:Synchronized
        lateinit var instance: MyApplication
    }

    init {
        instance = this
    }

    var email: String? = null
    var prefUtil: SharePreferenceUtils? = null

    override fun onCreate() {
        super.onCreate()
        prefUtil = SharePreferenceUtils.getInstance(applicationContext)
    }

}