package com.example.decathlonhiring.di

import com.example.decathlonhiring.ui.startgame.StartGameFragment
import com.example.decathlonhiring.ui.startgame.StartGameModule
import com.example.maticnetwork.di.scopes.PerFragment
import dagger.Module

import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

  @PerFragment
  @ContributesAndroidInjector(modules = [(StartGameModule::class)])
  abstract fun contributesStartGameFragment(): StartGameFragment
}