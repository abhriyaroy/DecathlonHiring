package com.example.maticnetwork.presenter.main

import com.example.maticnetwork.presenter.BasePresenter

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