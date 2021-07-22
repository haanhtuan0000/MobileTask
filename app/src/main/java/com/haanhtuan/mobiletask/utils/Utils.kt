package com.haanhtuan.mobiletask.utils

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.haanhtuan.mobiletask.MyApplication
import com.haanhtuan.mobiletask.base.BaseFragment
import com.haanhtuan.mobiletask.data.database.Item
import com.haanhtuan.mobiletask.databinding.DialogAddNewItemBinding

class Utils {
    companion object {
        fun newItemDialog(context: Context, addItem: (Item) -> Unit) {
            val binding: DialogAddNewItemBinding =
                DialogAddNewItemBinding.inflate(LayoutInflater.from(context))

            val alertDialog = AlertDialog.Builder(context).create()

            alertDialog.setButton(
                AlertDialog.BUTTON_POSITIVE,
                "OK"
            ) { dialog, _ ->
                addItem(
                    Item(
                        binding.newTextInput.text.toString(),
                        MyApplication.instance.email ?: ""
                    )
                )
                dialog.dismiss()
            }

            alertDialog.setView(binding.root)
            alertDialog.show()
        }

        fun errorSignInDialog(context: Context) {
            Log.e("tuan","err signinvvvv")
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setMessage("Your email/password is invalid")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
        }

        fun errorSignUpDialog(context: Context) {
            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setMessage("Please input valid email and password")
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK") { dialog, _ ->
                dialog.dismiss()
            }
            alertDialog.show()
        }
    }
}