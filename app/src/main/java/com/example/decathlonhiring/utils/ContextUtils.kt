package com.example.decathlonhiring.utils

import android.content.Context
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.annotation.StringRes

fun Context.showToast(message: String, duration: Int = LENGTH_LONG) {
  Toast.makeText(this, message, duration).show()
}

fun Context.stringRes(@StringRes stringResource: Int): String {
  return getString(stringResource)
}