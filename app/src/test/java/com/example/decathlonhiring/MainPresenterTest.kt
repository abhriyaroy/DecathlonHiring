package com.example.decathlonhiring

import com.example.decathlonhiring.presenter.main.MainContract.MainView
import com.example.decathlonhiring.presenter.main.MainPresenterImpl
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/**
 * I could not write tests for the other classes due to the limited amount of time, for which, I apologize.
 */
@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

  @Mock
  private lateinit var mainViewMock: MainView
  private lateinit var mainPresenter: MainPresenterImpl

  @Before
  fun setup() {
    mainPresenter = MainPresenterImpl()

    mainPresenter.attachView(mainViewMock)
  }

  @Test
  fun `should show start game screen on decorateView call success`() {
    mainPresenter.decorateView()

    verify(mainViewMock).showStartGameScreen()
  }

  @Test
  fun `should reset exit confirmation timer on notifyTimerExpired call success`() {
    mainPresenter.notifyTimerExpired()

    assertFalse(mainPresenter.isExitConfirmationShown)
  }

  @Test
  fun `should show exit confirmation and start timer on handleBackPress call success on when exit confirmation is not shown`() {
    mainPresenter.handleBackPress()

    assertTrue(mainPresenter.isExitConfirmationShown)
    verify(mainViewMock).showExitConfirmation()
    verify(mainViewMock).startBackPressedFlagResetTimer()
  }

  @Test
  fun `should show exit app on handleBackPress call success on when exit confirmation is shown`() {
    mainPresenter.isExitConfirmationShown = true

    mainPresenter.handleBackPress()

    assertTrue(mainPresenter.isExitConfirmationShown)
    verify(mainViewMock).exitApp()
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(mainViewMock)
    mainPresenter.detachView()
  }
}