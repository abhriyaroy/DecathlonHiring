package com.example.decathlonhiring.data

import com.example.decathlonhiring.data.Run.*
import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import java.util.*

interface Repository {
  // These return values could have been wrapped in Rx but that would cause unnecessary overhead
  fun getTargetScore(): Int

  @Throws(OutOfBatsmenException::class)
  fun getNextBatsman(): String

  fun getNextBowler(): String
  fun getNextBall(): Double
  fun updateOverCount(): Double
  fun getRunForDelivery(): Run
}

const val lowestScorePossible = 30
const val highestScorePossible = 90

class RepositoryImpl(private val backgroundScheduler: BackgroundScheduler) : Repository {

  private val batsmanStack = Stack<String>()
  private val bowlersMap = arrayListOf(
    Pair(1, "B1"),
    Pair(2, "B2"),
    Pair(3, "B3"),
    Pair(4, "B4"),
    Pair(5, "B5")
  )
  private var previousBowler = -1
  private var runsConceeded = 0
  private var overCount = 0.0

  init {
    batsmanStack.push("P5")
    batsmanStack.push("P4")
    batsmanStack.push("P3")
    batsmanStack.push("P2")
    batsmanStack.push("P1")
  }

  override fun getTargetScore(): Int {
    return Random().nextInt(highestScorePossible - lowestScorePossible) + lowestScorePossible
  }

  @Throws(OutOfBatsmenException::class)
  override fun getNextBatsman(): String {
    if (batsmanStack.isNotEmpty()) {
      return batsmanStack.pop()
    } else {
      throw OutOfBatsmenException()
    }
  }

  override fun getNextBowler(): String {
    val bowlerName: String
    if (previousBowler == -1 || runsConceeded < 10) {
      bowlerName = getRandomBowler()
    } else {
      bowlerName = getRandomHigherOrderBowler()
    }
    return bowlerName
  }

  override fun getNextBall(): Double {
    overCount += 0.1
    return overCount
  }

  override fun updateOverCount(): Double {
    overCount += 0.4
    return overCount
  }

  override fun getRunForDelivery(): Run {
    return when (Random().nextInt(6)) {
      0 -> ONE
      1 -> TWO
      2 -> THREE
      3 -> FOUR
      4 -> SIX
      5 -> WICKET
      else -> NO_BALL
    }
  }

  private fun getRandomBowler(): String {
    println(bowlersMap.toString())
    var randomValue = 0
    if (bowlersMap.size != 1) {
      randomValue = Random().nextInt(bowlersMap.size - 1)
    }
    previousBowler = bowlersMap[randomValue].first
    val name = bowlersMap[randomValue].second
    bowlersMap.removeAt(randomValue)
    return name
  }

  private fun getRandomHigherOrderBowler(): String {
    val betterBowlersList = arrayListOf<Triple<Int, Int, String>>()
    bowlersMap.forEachIndexed { index, pair ->
      if (pair.first > previousBowler) {
        betterBowlersList.add(Triple(index, pair.first, pair.second))
      }
    }
    if (betterBowlersList.size == 0) {
      return getRandomBowler()
    } else if (betterBowlersList.size == 1) {
      with(betterBowlersList[0]) {
        bowlersMap.removeAt(first)
        previousBowler = second
        return third
      }
    } else {
      with(betterBowlersList[Random().nextInt(betterBowlersList.size - 1)]) {
        bowlersMap.removeAt(first)
        previousBowler = second
        return third
      }
    }
  }
}

enum class Run {
  ONE, TWO, THREE, FOUR, SIX, WICKET, NO_BALL
}