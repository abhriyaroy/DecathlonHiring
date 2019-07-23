package com.example.decathlonhiring.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import com.example.decathlonhiring.R
import com.example.maticnetwork.presenter.main.MainContract
import com.example.maticnetwork.utils.showToast
import com.example.maticnetwork.utils.stringRes
import com.uber.autodispose.ScopeProvider
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), MainContract.MainView {

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

  override fun getScope(): ScopeProvider {
    return AndroidLifecycleScopeProvider.from(this.lifecycle, Lifecycle.Event.ON_DESTROY)
  }

  override fun showStartGameScreen() {
    /*supportFragmentManager.beginTransaction()
        .replace(R.id.mainContainerFragment, LandingFragment(), LANDING_FRAGMENT_TAG)
        .addToBackStack(LANDING_FRAGMENT_TAG)
        .commit()   */
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
