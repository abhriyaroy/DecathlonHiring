package com.example.decathlonhiring.utils

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.decathlonhiring.R

object AnimationUtil {
  fun showSlideUpAndFadeOutAnimation(context: Context?, view: View) {
    val slideUpAndFadeOut = AnimationUtils.loadAnimation(context, R.anim.slide_up_fade_out)
    slideUpAndFadeOut.fillAfter = true
    slideUpAndFadeOut.setAnimationListener(object : Animation.AnimationListener {
      override fun onAnimationRepeat(animation: Animation?) {
        // Do Nothing
      }

      override fun onAnimationEnd(animation: Animation?) {
        view.visibility = View.GONE
      }

      override fun onAnimationStart(animation: Animation?) {
        view.visibility = View.VISIBLE
      }
    })
    view.startAnimation(slideUpAndFadeOut)
  }
}