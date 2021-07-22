package com.haanhtuan.mobiletask.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.Repository
import com.haanhtuan.mobiletask.data.FirebaseRepository
import com.haanhtuan.mobiletask.data.LocalRepository
import com.haanhtuan.mobiletask.data.RepositoryInterface
import com.haanhtuan.mobiletask.data.database.Item

class HomeViewModel : ViewModel() {

    var showLoading = MutableLiveData(false)
    var repository: RepositoryInterface? = null

    fun initRepository(repositoryType: Repository, context: Context) {
        repository = null
        repository = when (repositoryType) {
            Repository.LOCAL -> {
                LocalRepository(context)
            }
            Repository.FIREBASE -> {
                FirebaseRepository()
            }
        }
    }

    fun getListItem(): LiveData<List<Item>>? {
        return repository?.getAllItems()
    }

    fun addItem(item: Item): LiveData<Boolean>? {
        return repository?.addNewItem(item)
    }

    fun delete(item: Item): LiveData<Boolean>? {
        return repository?.deleteItem(item)
    }

    fun signOutFirebase() {
        clearSavedData()
        val auth = Firebase.auth
        auth.signOut()
    }

    private fun clearSavedData() {
        MyApplication.instance.email = null
        MyApplication.instance.prefUtil?.remove("password")
        MyApplication.instance.prefUtil?.remove("email")
        MyApplication.instance.prefUtil?.remove("alreadyLoginBefore")
    }


}