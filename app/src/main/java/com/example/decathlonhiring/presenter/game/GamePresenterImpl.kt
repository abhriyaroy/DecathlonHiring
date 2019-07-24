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
    gameView?.updateScore(0)
    gameView?.updateTargetScore(repository.generateTargetScore())
    gameView?.updateStrikerName(strikerName)
    gameView?.updateRunnerName(runnerName)
    gameView?.updateCurrentDeliveryScore("0")
    gameView?.updateOverCount("0.0")
    gameView?.updateRunsRequired(repository.getTargetScore().toString())
    gameView?.updateBowlerName(repository.getNextBowler())
    gameView?.updateWickets(0)
  }

  override fun handleBowlClick() {
    val over = repository.getNextBall()
    try {
      val run = repository.getRunForDelivery()
      processRun(run)
    } catch (e: OutOfBatsmenException) {
      gameView?.showBowlingTeamWonMessage(getBowlingTeamWinningMessage())
    }
    updateView()
    checkIfBattingTeamHasWon()
    validateOver(over)
  }

  private fun processRun(run: Run) {
    if (run == ONE || run == THREE) {
      interchangeBatsmen()
    } else if (run == WICKET) {
      gameView?.updateWickets(repository.getWicketsLost())
      strikerName = repository.getNextBatsman()
      gameView?.updateStrikerName(strikerName)
    } else if (run == NO_BALL) {
      repository.cancelDeliveryDueToNoBall()
    }
  }

  private fun updateView() {
    gameView?.updateScore(repository.getCurrentScore())
    gameView?.updateRunsRequired(repository.getRequiredRunsToWin().toString())
  }

  private fun validateOver(over: Double) {
    var updatedOver = over
    if ((over + 0.4) % 1.0 == 0.0) {
      if (over + 0.4 == 5.0) {
        gameView?.showBowlingTeamWonMessage(getBowlingTeamWinningMessage())
        return
      }
      updatedOver += 0.4
      repository.updateOverCount()
      gameView?.updateBowlerName(repository.getNextBowler())
    }
    gameView?.updateCurrentDeliveryScore(repository.getCurrentDeliveryScore().toString())
    gameView?.updateOverCount(updatedOver.toString())
  }

  private fun checkIfBattingTeamHasWon() {
    if (repository.getCurrentDeliveryScore() <= 0) {
      gameView?.showBattingTeamWonMessage(getBattingTeamWinningMessage())
    }
  }

  private fun interchangeBatsmen() {
    gameView?.updateStrikerName(runnerName)
    gameView?.updateRunnerName(strikerName)
    val temp = runnerName
    runnerName = strikerName
    strikerName = temp
  }

  private fun getBowlingTeamWinningMessage(): String {
    val remainingRuns = repository.getRequiredRunsToWin()
    if (remainingRuns > 1) {
      return "Bowling team won by $remainingRuns runs"
    } else {
      return "Bowling team won by $remainingRuns run"
    }
  }

  private fun getBattingTeamWinningMessage(): String {
    val playersLeft = repository.getRemainingBatsmenCount() + 1
    return "Batting team won by ${playersLeft + 1} wickets"
  }
}