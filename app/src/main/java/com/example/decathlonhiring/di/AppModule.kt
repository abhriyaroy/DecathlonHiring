package com.example.decathlonhiring.di

import android.app.Application
import android.content.Context
import com.example.decathlonhiring.data.Repository
import com.example.decathlonhiring.data.RepositoryImpl
import com.example.decathlonhiring.di.scopes.PerApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

  @PerApplication
  @Provides
  fun providesContext(application: Application): Context = application

  @PerApplication
  @Provides
  fun providesRepository(): Repository = RepositoryImpl()
}