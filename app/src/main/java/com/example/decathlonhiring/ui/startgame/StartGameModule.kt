package com.example.decathlonhiring.ui.startgame

import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGamePresenter
import com.example.decathlonhiring.presenter.startgame.StartGamePresenterImpl
import com.example.decathlonhiring.di.scopes.PerFragment
import dagger.Module
import dagger.Provides

@Module
class StartGameModule {
  @PerFragment
  @Provides
  fun providesStartGamePresenter(): StartGamePresenter = StartGamePresenterImpl()
}