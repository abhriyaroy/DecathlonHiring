package com.example.decathlonhiring.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceHelper {
  fun getStringResource(@StringRes id: Int): String
}

class ResourceHelperImpl(private val context: Context) : ResourceHelper {
  override fun getStringResource(@StringRes id: Int): String {
    return context.stringRes(id)
  }
}