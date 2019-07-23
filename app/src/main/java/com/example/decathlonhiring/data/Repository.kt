package com.example.decathlonhiring.data

import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import java.util.*

interface Repository {
  // These return values could have been wrapped in Rx but that would cause unnecessary overhead
  fun getTargetScore(): Int

  @Throws(OutOfBatsmenException::class)
  fun getNextBatsman(): String

  fun getNextBowler(): String
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
    if (previousBowler == -1) {
      val randomValue = Random().nextInt(bowlersMap.size - 1)
      bowlerName = bowlersMap[randomValue].second
      bowlersMap.removeAt(randomValue)
      previousBowler = randomValue
    } else {
      bowlerName = getRandomHigherOrderBowler()
    }
    return bowlerName
  }

  private fun getRandomHigherOrderBowler(): String {
    val betterBowlersList = arrayListOf<Triple<Int, Int, String>>()
    bowlersMap.forEachIndexed { index, pair ->
      if (pair.first > previousBowler) {
        betterBowlersList.add(Triple(index, pair.first, pair.second))
      }
    }
    with(betterBowlersList[Random().nextInt(betterBowlersList.size - 1)]) {
      bowlersMap.removeAt(first)
      previousBowler = second
      return third
    }
  }
}