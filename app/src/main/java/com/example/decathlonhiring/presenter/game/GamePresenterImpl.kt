package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView

class GamePresenterImpl : GamePresenter {

  private var gameView: GameView? = null

  override fun attachView(view: GameView) {
    gameView = view
  }

  override fun detachView() {
    gameView = null
  }

}