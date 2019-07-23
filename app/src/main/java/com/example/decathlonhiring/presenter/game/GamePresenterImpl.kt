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
  private var targetScore: Int = 0

  override fun attachView(view: GameView) {
    gameView = view
  }

  override fun detachView() {
    gameView = null
  }

  override fun decorateView() {
    targetScore = repository.getTargetScore()
    gameView?.updateTargetScore(targetScore.toString())
    gameView?.updateStrikerName(repository.getNextBatsman())
    gameView?.updateRunnerName(repository.getNextBatsman())
    gameView?.updateBowlerName(repository.getNextBowler())
    gameView?.updateCurrentDeliveryScore("0")
    gameView?.updateOverCount("0.0")
    gameView?.updateRunsRequired(targetScore.toString())
  }

  override fun handleBowlClick() {
    println(repository.getNextBall())
    println()
  }

}