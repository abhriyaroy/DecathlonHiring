package com.example.decathlonhiring.ui.main

import com.example.maticnetwork.di.scopes.PerActivity
import com.example.maticnetwork.presenter.main.MainContract
import com.example.maticnetwork.presenter.main.MainPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
  @PerActivity
  @Provides
  fun providesMainPresenter(): MainContract.MainPresenter = MainPresenterImpl()
}