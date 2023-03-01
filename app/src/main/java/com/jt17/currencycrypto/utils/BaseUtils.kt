package com.jt17.currencycrypto.utils

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import kotlin.math.round

object BaseUtils {

    private lateinit var sharedPreferences: SharedPreferences
    fun copyToClipBoard(
        editText: EditText? = null,
        textView: TextView? = null,
        activity: Activity,
        context: Context
    ) {
        val edittextToCopy = editText?.text
        val textToCopy = textView?.text

        val clipboard =
            activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (textView != null) {
            val clipDataTextView = ClipData.newPlainText("text", textToCopy!!.toString())
            clipboard.setPrimaryClip(clipDataTextView)
        } else if (edittextToCopy != null) {
            val clipDataEditText = ClipData.newPlainText("null", edittextToCopy)
            clipboard.setPrimaryClip(clipDataEditText)
        }

        Toast.makeText(context, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
//        tvTextToPaste.text = clipboardManager.primaryClip?.getItemAt(0)?.text -> Paste from clipboard
    }

    infix fun idealDoubleResult(value: Double): String {
        return String.format("%.3f", value)
    }

    var themePosition = true
        get() = Hawk.get("isLight", true)
        set(value) {
            Hawk.put("isLight", value)
            field = value
        }
}