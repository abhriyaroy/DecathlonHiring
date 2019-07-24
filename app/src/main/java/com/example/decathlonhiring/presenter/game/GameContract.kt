package com.example.decathlonhiring.presenter.game

import com.example.decathlonhiring.presenter.BasePresenter

interface GameContract {

  interface GameView {
    fun updateScore(score: Int)
    fun updateTargetScore(score: String)
    fun updateStrikerName(name: String)
    fun updateRunnerName(name: String)
    fun updateBowlerName(name: String)
    fun updateCurrentDeliveryScore(score: String)
    fun updateOverCount(overCount: String)
    fun updateRunsRequired(runsRequired: String)
    fun updateWickets(wicketsLost: Int)
    fun showBattingTeamWonMessage(wickets: String)
    fun showBowlingTeamWonMessage(runs: String)
    fun updateStrikerScore(score : String)
    fun updateRunnerScore(score: String)
    fun showHalfCenturyAnimation(batsmanName : String)
  }

  interface GamePresenter : BasePresenter<GameView> {
    fun decorateView()
    fun handleBowlClick()
  }

}