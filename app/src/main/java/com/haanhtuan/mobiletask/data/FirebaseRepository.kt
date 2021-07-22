package com.haanhtuan.mobiletask.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.data.database.Item
import java.security.Timestamp
import java.util.*

class FirebaseRepository : RepositoryInterface {
    private val db = Firebase.firestore
    private val items = MutableLiveData<List<Item>>()
    override fun getAllItems(): LiveData<List<Item>>? {
        items.value = null
        db.collection("items")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                val itemsTemp = arrayListOf<Item>()
                for (doc in result) {
                    if (doc.data["email"] == MyApplication.instance.email) {
                        itemsTemp.add(
                            Item(
                                convertText(doc.data["text"].toString()),
                                doc.data["email"].toString(),
                                doc.id
                            )
                        )
                    }
                }

                items.value = itemsTemp
            }
            .addOnFailureListener { exception ->
                Log.e("tuan", "Error getting documents.", exception)
            }
        return items
    }

    private fun convertText(text: String): String {
        return text.replaceFirstChar { it.uppercaseChar() }.filter { !it.isWhitespace() }
    }

    override fun addNewItem(newItem: Item): LiveData<Boolean>? {
        val result = MutableLiveData<Boolean>()
        val docData = hashMapOf(
            "email" to newItem.email,
            "text" to newItem.msg,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection("items").add(docData)
            .addOnSuccessListener {
                result.value = true
            }.addOnFailureListener { exception ->
                result.value = false
                Log.e("tuan", "Error getting documents.", exception)
            }
        return result
    }

    override fun deleteItem(item: Item): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        db.collection("items").document(item.docId)
            .delete()
            .addOnSuccessListener {
                result.value = true
            }
            .addOnFailureListener {
                result.value = false
            }
        return result
    }
}