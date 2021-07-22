package com.haanhtuan.mobiletask.utils

import android.content.Context
import android.content.SharedPreferences

class SharePreferenceUtils constructor(context: Context) {
    companion object {
        private var instance: SharePreferenceUtils? = null
        private lateinit var _shareRefs: SharedPreferences
        private lateinit var editor: SharedPreferences.Editor
        private val lock = Any()

        fun getInstance(context: Context): SharePreferenceUtils {
            synchronized(lock) {
                if (instance == null) {
                    instance = SharePreferenceUtils(context)
                }
                return instance as SharePreferenceUtils
            }
        }
    }

    init {
        _shareRefs = context.applicationContext
            .getSharedPreferences(context.packageName + ".config", Context.MODE_PRIVATE)
    }

    @Synchronized
    fun saveBoolean(key: String, value: Boolean) {
        val editor = _shareRefs.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }

    @Synchronized
    fun getBoolean(key: String, defVa: Boolean) = _shareRefs.getBoolean(key, defVa)

    @Synchronized
    fun remove(key: String) {
        val editor = _shareRefs.edit()
        editor?.remove(key)
        editor?.apply()
    }

    @Synchronized
    fun clear() {
        editor.clear().apply()
    }

    @Synchronized
    fun saveString(key: String, value: String) {
        val editor = _shareRefs.edit()
        editor?.putString(key, value)
        editor?.apply()
    }

    @Synchronized
    fun getString(key: String, defVa: String) = _shareRefs.getString(key, defVa)

}