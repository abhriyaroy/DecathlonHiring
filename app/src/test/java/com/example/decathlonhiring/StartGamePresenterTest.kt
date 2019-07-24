package com.example.decathlonhiring

import com.example.decathlonhiring.presenter.startgame.StartGameContract.StartGameView
import com.example.decathlonhiring.presenter.startgame.StartGamePresenterImpl
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


/**
 * I could not write tests for the other classes due to the limited amount of time, for which, I apologize.
 */
@RunWith(MockitoJUnitRunner::class)
class StartGamePresenterTest {

  @Mock
  private lateinit var startGameViewMock: StartGameView
  private lateinit var startGamePresenter: StartGamePresenterImpl

  @Before
  fun setup() {
    startGamePresenter = StartGamePresenterImpl()

    startGamePresenter.attachView(startGameViewMock)
  }

  @Test
  fun `should show all the best message and start game screen on handleStartButtonClick call success`() {
    startGamePresenter.handleStartButtonClick()

    verify(startGameViewMock).showAllTheBestMessage()
    verify(startGameViewMock).showGameScreen()
  }

  @After
  fun tearDown() {
    verifyNoMoreInteractions(startGameViewMock)
    startGamePresenter.detachView()
  }
}