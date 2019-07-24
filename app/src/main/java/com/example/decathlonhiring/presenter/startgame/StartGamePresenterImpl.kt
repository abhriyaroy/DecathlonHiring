package com.example.decathlonhiring.presenter.startgame

import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGamePresenter
import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGameView

class StartGamePresenterImpl : StartGamePresenter {

  private var startGameView: StartGameView? = null

  override fun attachView(view: StartGameView) {
    startGameView = view
  }

  override fun detachView() {
    startGameView = null
  }

  override fun handleStartButtonClick() {
    startGameView?.showAllTheBestMessage()
    startGameView?.showGameScreen()
  }

}
