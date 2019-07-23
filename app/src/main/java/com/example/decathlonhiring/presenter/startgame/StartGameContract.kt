package com.example.decathlonhiring.presenter.startgame

import com.example.maticnetwork.presenter.BasePresenter
import com.example.maticnetwork.presenter.BaseView

interface StartGameContract {

  interface StartGameView : BaseView {
    fun showGameScreen()
  }

  interface StartGamePresenter : BasePresenter<StartGameView> {
    fun handleStartButtonClick()
  }
}