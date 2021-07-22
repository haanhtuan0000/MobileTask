package com.haanhtuan.mobiletask.data

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.haanhtuan.mobiletask.data.database.AppDatabase
import com.haanhtuan.mobiletask.data.database.Item
import com.haanhtuan.mobiletask.data.database.ItemDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class LocalRepository(context: Context) : RepositoryInterface {

    private var itemDao: ItemDao? = null

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)

    init {
        itemDao =
            Room.databaseBuilder(context, AppDatabase::class.java, "item.db").build().itemDao()
    }

    override fun getAllItems(): LiveData<List<Item>> {
        val items = MutableLiveData<List<Item>>()
        executorService.execute {
            var temp = itemDao?.getAll()
            items.postValue(temp)
        }
        return items
    }

    override fun addNewItem(newItem: Item): LiveData<Boolean> {
        executorService.execute {
            itemDao?.insert(newItem)
        }

        return MutableLiveData(true)
    }

    override fun deleteItem(item: Item): LiveData<Boolean> {
        executorService.execute {
            itemDao?.delete(item.id)
        }

        return MutableLiveData(true)
    }
}