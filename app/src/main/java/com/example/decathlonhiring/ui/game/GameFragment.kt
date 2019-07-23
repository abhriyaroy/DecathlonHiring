package com.example.decathlonhiring.ui.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.decathlonhiring.R
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject

class GameFragment : Fragment(), GameView {

  @Inject
  internal lateinit var gamePresenter: GamePresenter
  private lateinit var fragmentView: View

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_game, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    fragmentView = view
    attachClickListeners()
    gamePresenter.attachView(this)
    gamePresenter.decorateView()
  }

  override fun onDestroy() {
    gamePresenter.detachView()
    super.onDestroy()
  }

  override fun updateTargetScore(score: String) {
    fragmentView.targetTextView.text = score
  }

  override fun updateStrikerName(name: String) {
    fragmentView.strikerBatsmanTextView.text = name
  }

  override fun updateRunnerName(name: String) {
    fragmentView.runnerBatsmanTextView.text = name
  }

  override fun updateRunsRequired(runsRequired: String) {
    fragmentView.requiredRunsTextView.text = runsRequired
  }

  override fun updateOverCount(overCount: String) {
    fragmentView.oversTextView.text = overCount
  }

  override fun updateCurrentDeliveryScore(score: String) {
    fragmentView.currentBallTextView.text = score
  }

  override fun updateBowlerName(name: String) {

  }

  private fun attachClickListeners() {
    fragmentView.bowlButton.setOnClickListener {
      gamePresenter.handleBowlClick()
    }
  }

}
