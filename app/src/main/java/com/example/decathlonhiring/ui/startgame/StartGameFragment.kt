package com.example.decathlonhiring.ui.startgame


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.decathlonhiring.R
import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGamePresenter
import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGameView
import com.example.decathlonhiring.ui.game.GameFragment
import com.example.decathlonhiring.utils.showToast
import com.example.decathlonhiring.utils.stringRes
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_start_game.view.*
import javax.inject.Inject

class StartGameFragment : Fragment(), StartGameView {

  @Inject
  internal lateinit var startGamePresenter: StartGamePresenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_start_game, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    initClickListeners(view)
    startGamePresenter.attachView(this)
  }

  override fun onDestroy() {
    startGamePresenter.detachView()
    super.onDestroy()
  }

  override fun showGameScreen() {
    activity?.supportFragmentManager?.beginTransaction()
      ?.replace(R.id.mainContainer, GameFragment())
      ?.commit()
  }

  override fun showAllTheBestMessage() {
    context?.showToast(context!!.stringRes(R.string.start_game_fragment_all_the_best_message))
  }

  private fun initClickListeners(view: View) {
    view.startGameButton.setOnClickListener {
      startGamePresenter.handleStartButtonClick()
    }
  }

}
