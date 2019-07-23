package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import com.example.decathlonhiring.ui.MainScheduler

class GamePresenterImpl(
  private val repository: Repository,
  private val mainScheduler: MainScheduler
) : GamePresenter {

  private var gameView: GameView? = null

  override fun attachView(view: GameView) {
    gameView = view
  }

  override fun detachView() {
    gameView = null
  }

}