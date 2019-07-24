package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.data.Run
import com.example.decathlonhiring.data.Run.*
import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView

class GamePresenterImpl(
  private val repository: Repository
) : GamePresenter {

  private var gameView: GameView? = null
  private var targetScore: Int = 0
  private var currentScore: Int = 0
  private var currentDeliveryScore: Int = 0
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
    gameView?.updateScore(0)
    gameView?.updateTargetScore(targetScore.toString())
    gameView?.updateStrikerName(strikerName)
    gameView?.updateRunnerName(runnerName)
    gameView?.updateCurrentDeliveryScore("0")
    gameView?.updateOverCount("0.0")
    gameView?.updateRunsRequired(targetScore.toString())
    gameView?.updateBowlerName(repository.getNextBowler())
    gameView?.updateWickets(0)
  }

  override fun handleBowlClick() {
    val over = repository.getNextBall()
    try {
      val run = repository.getRunForDelivery()
      processRun(run)
      currentDeliveryScore = repository.getRunValue(run)
      currentScore += currentDeliveryScore
      requiredRuns = targetScore - currentScore
    } catch (e: OutOfBatsmenException) {
      gameView?.showBowlingTeamWonMessage(getBowlingTeamWinningMessage(over))
    }
    updateView()
    checkIfBattingTeamHasWon()
    validateOver(over)
  }

  private fun updateView() {
    gameView?.updateScore(currentScore)
    gameView?.updateRunsRequired(requiredRuns.toString())
  }

  private fun validateOver(over: Double) {
    var updatedOver = over
    if ((over + 0.4) % 1.0 == 0.0) {
      if (over + 0.4 == 5.0) {
        gameView?.showBowlingTeamWonMessage(getBowlingTeamWinningMessage(over))
        return
      }
      updatedOver += 0.4
      repository.updateOverCount()
      gameView?.updateBowlerName(repository.getNextBowler())
    }
    gameView?.updateCurrentDeliveryScore(currentDeliveryScore.toString())
    gameView?.updateOverCount(updatedOver.toString())
  }

  private fun checkIfBattingTeamHasWon() {
    if (requiredRuns <= 0) {
      gameView?.showBattingTeamWonMessage(getBattingTeamWinningMessage())
    }
  }

  private fun processRun(run: Run) {
    if (run == ONE || run == THREE) {
      interchangeBatsmen()
    } else if (run == WICKET) {
      val wicketsLost = 5 - (repository.getRemainingBatsmenCount() + 1)
      gameView?.updateWickets(wicketsLost)
      strikerName = repository.getNextBatsman()
      gameView?.updateStrikerName(strikerName)
    } else if (run == NO_BALL) {
      repository.cancelDeliveryDueToNoBall()
    }
  }

  private fun interchangeBatsmen() {
    gameView?.updateStrikerName(runnerName)
    gameView?.updateRunnerName(strikerName)
    val temp = runnerName
    runnerName = strikerName
    strikerName = temp
  }

  private fun getBowlingTeamWinningMessage(over: Double): String {
    val ballsLeftInCurrentOver = repository.getRemainingBallsInCurrentOverCount()
    val oversLeft = repository.getRemainingOversCount()
    if (ballsLeftInCurrentOver > 1)
      return "Bowling team won by $oversLeft.$ballsLeftInCurrentOver overs"
    else if (oversLeft == 1) {
      return "Bowling team won by $oversLeft over"
    } else {
      return "Bowling team won by $oversLeft overs"
    }
  }

  private fun getBattingTeamWinningMessage(): String {
    val playersLeft = repository.getRemainingBatsmenCount() + 1
    return "Batting team won by ${playersLeft + 1} wickets"
  }
}