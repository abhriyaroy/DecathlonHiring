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
import javax.inject.Inject

class GameFragment : Fragment(), GameView {

  @Inject
  internal lateinit var gamePresenter: GamePresenter

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    AndroidSupportInjection.inject(this)
    return inflater.inflate(R.layout.fragment_game, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    gamePresenter.attachView(this)
  }

  override fun onDestroy() {
    gamePresenter.detachView()
    super.onDestroy()
  }

}
