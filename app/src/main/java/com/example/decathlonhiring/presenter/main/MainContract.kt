package com.example.decathlonhiring.presenter.main

import com.example.decathlonhiring.presenter.BasePresenter

interface MainContract {

  interface MainView {
    fun showStartGameScreen()
    fun showExitConfirmation()
    fun startBackPressedFlagResetTimer()
    fun exitApp()
  }

  interface MainPresenter : BasePresenter<MainView> {
    fun decorateView()
    fun handleBackPress()
    fun notifyTimerExpired()
  }
}