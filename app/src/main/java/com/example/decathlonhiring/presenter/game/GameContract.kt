package com.example.decathlonhiring.presenter.game

import com.example.maticnetwork.presenter.BasePresenter

interface GameContract {

  interface GameView {
    fun updateTargetScore(score: String)
    fun updateStrikerName(name: String)
    fun updateRunnerName(name: String)
    fun updateBowlerName(name: String)
    fun updateCurrentDeliveryScore(score : String)
    fun updateOverCount(overCount : String)
    fun updateRunsRequired(runsRequired : String)
  }

  interface GamePresenter : BasePresenter<GameView> {
    fun decorateView()
    fun handleBowlClick()
  }

}