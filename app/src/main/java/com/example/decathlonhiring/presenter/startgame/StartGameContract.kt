package com.example.decathlonhiring.presenter.startgame

import com.example.decathlonhiring.presenter.BasePresenter

interface StartGameContract {

  interface StartGameView {
    fun showGameScreen()
    fun showAllTheBestMessage()
  }

  interface StartGamePresenter : BasePresenter<StartGameView> {
    fun handleStartButtonClick()
  }
}