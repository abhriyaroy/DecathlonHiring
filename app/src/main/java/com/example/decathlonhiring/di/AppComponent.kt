package com.example.decathlonhiring.di

import android.app.Application
import com.example.decathlonhiring.DecathlonHiringApplication
import com.example.decathlonhiring.di.scopes.PerApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule

@PerApplication
@Component(
    modules = [(AndroidInjectionModule::class), (AppModule::class), (ActivityBuilder::class)])
interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    fun application(application: Application): Builder

    fun build(): AppComponent
  }

  fun inject(app: DecathlonHiringApplication)

}