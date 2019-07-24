package com.example.decathlonhiring.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.decathlonhiring.R
import com.example.decathlonhiring.presenter.main.MainContract
import com.example.decathlonhiring.presenter.main.MainContract.MainView
import com.example.decathlonhiring.ui.startgame.StartGameFragment
import com.example.decathlonhiring.utils.showToast
import com.example.decathlonhiring.utils.stringRes
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainView {

  @Inject
  internal lateinit var mainPresenter: MainContract.MainPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    AndroidInjection.inject(this)
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    mainPresenter.attachView(this)
    mainPresenter.decorateView()
  }

  override fun onDestroy() {
    mainPresenter.detachView()
    super.onDestroy()
  }

  override fun onBackPressed() {
    mainPresenter.handleBackPress()
  }

  override fun showStartGameScreen() {
    supportFragmentManager.beginTransaction()
      .replace(R.id.mainContainer, StartGameFragment())
      .commit()
  }

  override fun showExitConfirmation() {
    showToast(stringRes(R.string.main_activity_exit_confirmation_message))
  }

  override fun startBackPressedFlagResetTimer() {
    withDelayOnMain(TimeUnit.SECONDS.toMillis(2)) { mainPresenter.notifyTimerExpired() }
  }

  override fun exitApp() {
    finish()
  }

  private fun withDelayOnMain(delay: Long, block: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed(Runnable(block), delay)
  }

}
