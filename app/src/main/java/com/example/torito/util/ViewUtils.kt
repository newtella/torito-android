package com.example.torito.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast

//toast added to app context, so you can called from activities.
fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show() {
  visibility = View.VISIBLE
}

fun ProgressBar.hide() {
    visibility = View.GONE
}