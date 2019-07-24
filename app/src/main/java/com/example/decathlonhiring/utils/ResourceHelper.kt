package com.example.decathlonhiring.utils

import android.content.Context
import androidx.annotation.StringRes

interface ResourceHelper {
  fun getStringResource(@StringRes id: Int): String
  fun getStringResource(@StringRes id: Int, argument: Int): String
}

class ResourceHelperImpl(private val context: Context) : ResourceHelper {
  override fun getStringResource(@StringRes id: Int): String {
    return context.stringRes(id)
  }

  override fun getStringResource(id: Int, argument: Int): String {
    return context.getString(id, argument)
  }
}