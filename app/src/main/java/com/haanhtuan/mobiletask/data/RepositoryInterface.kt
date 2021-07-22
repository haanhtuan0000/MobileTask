package com.haanhtuan.mobiletask.data

import androidx.lifecycle.LiveData
import com.haanhtuan.mobiletask.data.database.Item

interface RepositoryInterface {
    fun getAllItems(): LiveData<List<Item>>?
    fun addNewItem(newItem: Item): LiveData<Boolean>?
    fun deleteItem(item: Item): LiveData<Boolean>?
}