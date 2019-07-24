package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.R
import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.data.Run
import com.example.decathlonhiring.data.Run.*
import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import com.example.decathlonhiring.utils.ResourceHelper

private const val INITIAL_SCORE = 0
private const val INITIAL_OVER_COUNT = 0.0F
private const val INITIAL_WICKETS_LOST = 0
private const val HALF_CENTURY_SCORE = 50
private const val OVER_COUNT_HELPER = 0.4
private const val ONE_OVER_COUNT = 1.0

class GamePresenterImpl(
  private val repository: Repository,
  private val resourceHelper: ResourceHelper
) : GamePresenter {

  private var gameView: GameView? = null
  private var strikerName = ""
  private var runnerName = ""
  private val celebrationsSet = hashSetOf<String>()

  override fun attachView(view: GameView) {
    gameView = view
  }

  override fun detachView() {
    gameView = null
  }

  override fun decorateView() {
    strikerName = repository.getNextBatsman()
    runnerName = repository.getNextBatsman()
    gameView?.updateScore(INITIAL_SCORE)
    gameView?.updateTargetScore(repository.generateTargetScore())
    gameView?.updateStrikerName(strikerName)
    gameView?.updateRunnerName(runnerName)
    gameView?.updateCurrentDeliveryScore("$INITIAL_SCORE")
    gameView?.updateOverCount("$INITIAL_OVER_COUNT")
    gameView?.updateRunsRequired(repository.getTargetScore().toString())
    gameView?.updateBowlerName(repository.getNextBowler())
    gameView?.updateWickets(INITIAL_WICKETS_LOST)
    gameView?.updateStrikerScore("$strikerName ($INITIAL_OVER_COUNT)*")
    gameView?.updateRunnerScore("$runnerName ($INITIAL_OVER_COUNT)")
    gameView?.updateWickets(INITIAL_WICKETS_LOST)
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
    repository.incrementBatsmanScore(strikerName, run)
    if (run == ONE || run == THREE) {
      gameView?.animateBatsmen()
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
    with(repository.getBatsmanScore(strikerName)) {
      if (this >= HALF_CENTURY_SCORE && !celebrationsSet.contains("$strikerName ($HALF_CENTURY_SCORE)*")) {
        celebrationsSet.add(strikerName)
        gameView?.showHalfCenturyAnimation(strikerName)
      }
      gameView?.updateStrikerScore("$strikerName ($this)*")
    }
    with(repository.getBatsmanScore(runnerName)) {
      if (this >= HALF_CENTURY_SCORE && !celebrationsSet.contains(runnerName)) {
        celebrationsSet.add(runnerName)
        gameView?.showHalfCenturyAnimation("$runnerName ($HALF_CENTURY_SCORE)*")
      }
      gameView?.updateRunnerScore("$runnerName ($this)")
    }
  }

  private fun validateOver(over: Double) {
    var updatedOver = over
    if ((over + OVER_COUNT_HELPER) % ONE_OVER_COUNT == 0.0) {
      if (over + OVER_COUNT_HELPER == 5.0) {
        gameView?.showBowlingTeamWonMessage(getBowlingTeamWinningMessage())
        return
      }
      updatedOver += OVER_COUNT_HELPER
      repository.updateOverCount()
      gameView?.updateBowlerName(repository.getNextBowler())
    }
    gameView?.updateCurrentDeliveryScore(repository.getCurrentDeliveryScore().toString())
    gameView?.updateOverCount(updatedOver.toString())
  }

  private fun checkIfBattingTeamHasWon() {
    if (repository.getRequiredRunsToWin() <= 0) {
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
      return resourceHelper.getStringResource(
        R.string.game_fragment_bowling_team_wins_by_more_than_one_run_message,
        remainingRuns
      )
    } else {
      return resourceHelper.getStringResource(
        R.string.game_fragment_bowling_team_wins_by_more_than_one_run_message,
        remainingRuns
      )
    }
  }

  private fun getBattingTeamWinningMessage(): String {
    val playersLeft = repository.getRemainingBatsmenCount() + 1
    return resourceHelper.getStringResource(
      R.string.game_fragment_batting_team_wins_message,
      playersLeft + 1
    )
  }
}