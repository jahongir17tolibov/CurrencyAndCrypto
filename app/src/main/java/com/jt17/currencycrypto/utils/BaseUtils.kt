package com.jt17.currencycrypto.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.time.Duration

object BaseUtils {

    fun Fragment.copyToClipBoard(
        editText: EditText? = null,
        textView: TextView? = null
    ) {
        val edittextToCopy = editText?.text
        val textToCopy = textView?.text

        val clipboard =
            requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (textView != null) {
            val clipDataTextView = ClipData.newPlainText("text", textToCopy!!.toString())
            clipboard.setPrimaryClip(clipDataTextView)
        } else if (edittextToCopy != null) {
            val clipDataEditText = ClipData.newPlainText("null", edittextToCopy)
            clipboard.setPrimaryClip(clipDataEditText)
        }

        showToast("Text copied to clipboard")
//        tvTextToPaste.text = clipboardManager.primaryClip?.getItemAt(0)?.text -> Paste from clipboard
    }

    infix fun Fragment.idealDoubleResult(value: Double): String {
        return String.format("%.3f", value)
    }

    fun Fragment.showToast(msg: String?, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), msg, duration).show()
    }

}