package com.example.decathlonhiring.ui.game


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener
import androidx.fragment.app.Fragment
import com.example.decathlonhiring.R
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import com.example.decathlonhiring.utils.AnimationUtil
import com.example.decathlonhiring.utils.stringRes
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject

private const val INITIAL_PROGRESS_VALUE = 0.0f

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

  override fun animateBatsmen() {
    fragmentView.motionContainer.transitionToEnd()
  }

  override fun updateScore(score: Int) {
    fragmentView.runsTextView.text =
      activity!!.stringRes(R.string.game_fragment_score_textView_message, score)
  }

  override fun updateTargetScore(score: String) {
    fragmentView.targetTextView.text =
      activity!!.stringRes(R.string.game_fragment_target_score_textView_message, score)
  }

  override fun updateStrikerName(name: String) {
    fragmentView.strikerBatsmanTextView.text = name
  }

  override fun updateRunnerName(name: String) {
    fragmentView.runnerBatsmanTextView.text = name
  }

  override fun updateRunsRequired(runsRequired: String) {
    fragmentView.requiredRunsTextView.text =
      activity!!.stringRes(R.string.game_fragment_runs_required_textView_message, runsRequired)
  }

  override fun updateOverCount(overCount: String) {
    fragmentView.oversTextView.text =
      activity!!.stringRes(R.string.game_fragment_over_count_textView_message, overCount)
  }

  override fun updateCurrentDeliveryScore(score: String) {
    fragmentView.currentBallTextView.text =
      activity!!.stringRes(R.string.game_fragment_current_ball_textView_message, score)
  }

  override fun updateBowlerName(name: String) {
    fragmentView.bowlerNameTextView.text =
      activity!!.stringRes(R.string.game_fragment_bowler_name_textView_message, name)
  }

  override fun updateWickets(wicketsLost: Int) {
    fragmentView.wicketsTextView.text =
      activity!!.stringRes(R.string.game_fragment_bowler_name_textView_message, wicketsLost)
  }

  override fun showBattingTeamWonMessage(wickets: String) {
    with(AlertDialog.Builder(activity)) {
      setCancelable(false)
      setTitle(activity!!.stringRes(R.string.game_fragment_match_result_dialog_title))
      setMessage(wickets)
      show()
    }
  }

  override fun showBowlingTeamWonMessage(runs: String) {
    with(AlertDialog.Builder(activity)) {
      setCancelable(false)
      setTitle(activity!!.stringRes(R.string.game_fragment_match_result_dialog_title))
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

  override fun showHalfCenturyAnimation(message: String) {
    with(fragmentView.halfCenturyCelebrationTextView) {
      text = message
      AnimationUtil.showSlideUpAndFadeOutAnimation(activity, this)
    }
  }

  private fun attachClickListeners() {
    fragmentView.bowlButton.setOnClickListener {
      gamePresenter.handleBowlClick()
    }
    fragmentView.motionContainer.setTransitionListener(
      object : TransitionListener {
        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
          // Do nothing
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
          fragmentView.motionContainer.progress = INITIAL_PROGRESS_VALUE
        }
      }
    )
  }

}
