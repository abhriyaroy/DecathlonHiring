package com.example.decathlonhiring.di

import android.app.Application
import android.content.Context
import com.example.decathlonhiring.data.BackgroundScheduler
import com.example.decathlonhiring.data.BackgroundSchedulerImpl
import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.data.RepositoryImpl
import com.example.decathlonhiring.di.scopes.PerApplication
import com.example.decathlonhiring.ui.MainScheduler
import com.example.decathlonhiring.ui.MainSchedulerImpl
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @PerApplication
  @Provides
  fun providesContext(application: Application): Context = application

  @PerApplication
  @Provides
  fun providesMainThread(): MainScheduler = MainSchedulerImpl()

  @PerApplication
  @Provides
  fun providesBackgroundThread(): BackgroundScheduler = BackgroundSchedulerImpl()

  @PerApplication
  @Provides
  fun providesRepository(backgroundScheduler: BackgroundScheduler): Repository =
    RepositoryImpl(backgroundScheduler)
}