package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.data.Run.*
import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import com.example.decathlonhiring.ui.MainScheduler

class GamePresenterImpl(
  private val repository: Repository,
  private val mainScheduler: MainScheduler
) : GamePresenter {

  private var gameView: GameView? = null
  private var targetScore: Int = 0
  private var currentScore: Int = 0
  private var requiredRuns: Int = 0
  private var strikerName = ""
  private var runnerName = ""

  override fun attachView(view: GameView) {
    gameView = view
  }

  override fun detachView() {
    gameView = null
  }

  override fun decorateView() {
    strikerName = repository.getNextBatsman()
    runnerName = repository.getNextBatsman()
    targetScore = repository.getTargetScore()
    gameView?.updateTargetScore(targetScore.toString())
    gameView?.updateStrikerName(strikerName)
    gameView?.updateRunnerName(runnerName)
    gameView?.updateCurrentDeliveryScore("0")
    gameView?.updateOverCount("0.0")
    gameView?.updateRunsRequired(targetScore.toString())
  }

  override fun handleBowlClick() {
    try {
      val over = repository.getNextBall()
      val run = repository.getRunForDelivery()
      when (run) {
        ONE -> {
          currentScore += 1
          interchangeBatsmen()
        }
        TWO -> currentScore += 2
        THREE -> {
          currentScore += 3
          interchangeBatsmen()
        }
        FOUR -> {
          currentScore += 4
        }
        SIX -> currentScore += 6
        WICKET -> {
          strikerName = repository.getNextBatsman()
          gameView?.updateStrikerName(strikerName)
        }
        NO_BALL -> {
          currentScore += 1
          repository.cancelDeliveryDueToNoBall()
        }
      }
      requiredRuns = targetScore - currentScore
    } catch (e: OutOfBatsmenException) {
      // bowling team has won
    }
    validateOver()
    checkIfBowlingTeamHasWon()
    checkIfBattingTeamHasWon()
  }

  private fun interchangeBatsmen() {
    gameView?.updateStrikerName(runnerName)
    gameView?.updateRunnerName(strikerName)
    val temp = runnerName
    runnerName = strikerName
    strikerName = temp
  }

  private fun validateOver() {
    
  }

  private fun checkIfBattingTeamHasWon() {

  }

  private fun checkIfBowlingTeamHasWon() {

  }

}