package com.haanhtuan.mobiletask.data.database

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {

    @Query("SELECT * FROM items ORDER BY id DESC")
    fun getAll(): List<Item>

    @Insert
    fun insert(item: Item)

    @Query("DELETE FROM items WHERE id = :id")
    fun delete(id: Long)
//
//    @Query("SELECT * FROM logs ORDER BY id DESC")
//    fun selectAllLogsCursor(): Cursor
//
//    @Query("SELECT * FROM logs WHERE id = :id")
//    fun selectLogById(id: Long): Cursor?

}