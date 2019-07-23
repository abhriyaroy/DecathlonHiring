package com.example.decathlonhiring.ui.game

import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GamePresenterImpl
import com.example.maticnetwork.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

@Module
class GameModule {

  @PerFragment
  @Provides
  fun providesGamePresenter(): GamePresenter = GamePresenterImpl()

}