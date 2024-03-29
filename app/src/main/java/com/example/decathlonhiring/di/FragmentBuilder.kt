package com.example.decathlonhiring.di

import com.example.decathlonhiring.di.scopes.PerFragment
import com.example.decathlonhiring.ui.game.GameFragment
import com.example.decathlonhiring.ui.game.GameModule
import com.example.decathlonhiring.ui.startgame.StartGameFragment
import com.example.decathlonhiring.ui.startgame.StartGameModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

  @PerFragment
  @ContributesAndroidInjector(modules = [(StartGameModule::class)])
  abstract fun contributesStartGameFragment(): StartGameFragment

  @PerFragment
  @ContributesAndroidInjector(modules = [(GameModule::class)])
  abstract fun contributesGameFragment(): GameFragment

}