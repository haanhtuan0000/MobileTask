package com.haanhtuan.mobiletask.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(val msg: String, var email: String = "", var docId: String = "") {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
