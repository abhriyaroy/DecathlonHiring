package com.example.decathlonhiring.ui.main

import com.example.decathlonhiring.di.scopes.PerActivity
import com.example.decathlonhiring.presenter.main.MainContract
import com.example.decathlonhiring.presenter.main.MainPresenterImpl
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
  @PerActivity
  @Provides
  fun providesMainPresenter(): MainContract.MainPresenter = MainPresenterImpl()
}