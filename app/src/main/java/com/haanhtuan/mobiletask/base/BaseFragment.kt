package com.haanhtuan.mobiletask.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.haanhtuan.mobiletask.R

open class BaseFragment : Fragment() {

    private var progressDialog: AlertDialog? = null

    fun showLoading() {
        hideLoading()
        if (activity != null) {
            val view = layoutInflater.inflate(R.layout.progress_custom, null, false)
            val builder =
                AlertDialog.Builder(requireActivity(), R.style.MyProgressDialogTheme).setView(view)
            if (activity?.isFinishing == false) {
                progressDialog = builder.create()
                progressDialog?.apply {
                    setCanceledOnTouchOutside(false)
                    setCancelable(true)
                    show()
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    findViewById<ImageView>(R.id.spinnerImageView)?.let {
                        val spinner = it.background as AnimationDrawable
                        spinner.start()
                    }
                }
            }
        }
    }

    fun hideLoading() {
        progressDialog?.dismiss()
    }

    fun removeAllFragmentInBackStack() {
        var count = activity?.supportFragmentManager?.backStackEntryCount ?: 0
        while (count > 0) {
            activity?.supportFragmentManager?.popBackStack()
            count--
        }
    }

    fun hideKeyboard() {
        activity?.currentFocus?.let {
            (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                it.windowToken,
                0
            )
        }
    }

    fun goToScreen(fragment: BaseFragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.main_container, fragment)
            ?.addToBackStack(fragment::class.java.canonicalName)
            ?.commit()
    }


}