package com.example.decathlonhiring.presenter.main

import com.example.decathlonhiring.presenter.main.MainContract.MainPresenter
import com.example.decathlonhiring.presenter.main.MainContract.MainView

class MainPresenterImpl : MainPresenter {
  private var mainView: MainView? = null

  private var isExitConfirmationShown = false

  override fun attachView(view: MainView) {
    mainView = view
  }

  override fun detachView() {
    mainView = null
  }

  override fun decorateView() {
    mainView?.showStartGameScreen()
  }

  override fun handleBackPress() {
    if (!isExitConfirmationShown) {
      mainView?.showExitConfirmation()
      isExitConfirmationShown = true
      mainView?.startBackPressedFlagResetTimer()
    } else {
      mainView?.exitApp()
    }
  }

  override fun notifyTimerExpired() {
    isExitConfirmationShown = false
  }

}