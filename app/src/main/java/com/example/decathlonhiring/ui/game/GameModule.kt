package com.example.decathlonhiring.ui.game

import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.di.scopes.PerFragment
import com.example.decathlonhiring.presenter.game.GameContract.GamePresenter
import com.example.decathlonhiring.presenter.game.GamePresenterImpl
import com.example.decathlonhiring.utils.ResourceHelper
import dagger.Module
import dagger.Provides

@Module
class GameModule {

  @PerFragment
  @Provides
  fun providesGamePresenter(repository: Repository, resourceHelper: ResourceHelper): GamePresenter =
    GamePresenterImpl(repository, resourceHelper)

}