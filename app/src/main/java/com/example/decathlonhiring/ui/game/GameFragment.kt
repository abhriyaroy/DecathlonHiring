package com.example.decathlonhiring.ui.game


import android.app.AlertDialog
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

  override fun updateScore(score: Int) {
    fragmentView.runsTextView.text = "Runs : $score"
  }

  override fun updateTargetScore(score: String) {
    fragmentView.targetTextView.text = "Target: $score"
  }

  override fun updateStrikerName(name: String) {
    fragmentView.strikerBatsmanTextView.text = name
  }

  override fun updateRunnerName(name: String) {
    fragmentView.runnerBatsmanTextView.text = name
  }

  override fun updateRunsRequired(runsRequired: String) {
    fragmentView.requiredRunsTextView.text = "Runs Required: $runsRequired"
  }

  override fun updateOverCount(overCount: String) {
    fragmentView.oversTextView.text = "Overs: $overCount"
  }

  override fun updateCurrentDeliveryScore(score: String) {
    fragmentView.currentBallTextView.text = "Current ball run: $score"
  }

  override fun updateBowlerName(name: String) {
    fragmentView.bowlerNameTextView.text = "Bowler: $name"
  }

  override fun updateWickets(wicketsLost: Int) {
    fragmentView.wicketsTextView.text = "Wickets Lost: $wicketsLost"
  }

  override fun showBattingTeamWonMessage(wickets: String) {
    with(AlertDialog.Builder(activity)) {
      setCancelable(false)
      setTitle("Match Result")
      setMessage(wickets)
      show()
    }
  }

  override fun showBowlingTeamWonMessage(runs: String) {
    with(AlertDialog.Builder(activity)) {
      setCancelable(false)
      setTitle("Match Result")
      setMessage(runs)
      show()
    }
  }

  override fun updateStrikerScore(score: String) {
    fragmentView.strikerScoreTextView.text = score
  }

  override fun updateRunnerScore(score: String) {
    fragmentView.runnerScoreTextView.text = score
  }

  override fun showHalfCenturyAnimation(batsmanName: String) {

  }

  private fun attachClickListeners() {
    fragmentView.bowlButton.setOnClickListener {
      gamePresenter.handleBowlClick()
    }
  }

}
