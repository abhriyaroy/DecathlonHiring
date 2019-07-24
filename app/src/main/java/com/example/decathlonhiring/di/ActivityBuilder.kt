package com.example.decathlonhiring.di

import com.example.decathlonhiring.ui.main.MainActivity
import com.example.decathlonhiring.ui.main.MainActivityModule
import com.example.decathlonhiring.di.scopes.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {
  @PerActivity
  @ContributesAndroidInjector(modules = [(MainActivityModule::class), (FragmentBuilder::class)])
  abstract fun mainActivityInjector(): MainActivity
}