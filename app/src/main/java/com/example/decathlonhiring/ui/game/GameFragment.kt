package com.example.decathlonhiring.ui.game


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.decathlonhiring.R
import com.example.decathlonhiring.presenter.game.GameContract.GameView
import dagger.android.support.AndroidSupportInjection

class GameFragment : Fragment(), GameView {

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_game, container, false)
  }



}
