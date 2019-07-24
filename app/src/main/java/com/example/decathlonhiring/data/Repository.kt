package com.example.decathlonhiring.data

import com.example.decathlonhiring.data.Run.*
import com.example.decathlonhiring.exceptions.OutOfBatsmenException
import java.util.*

/**
 * Usually I would use RxJava to wrap these return values, however, in this case threading would
 * just be an overhead for such simple operations rather than improving the performance.
 *
 * Thus normal data types have been used.
 */
interface Repository {
  fun getTargetScore(): Int

  @Throws(OutOfBatsmenException::class)
  fun getNextBatsman(): String

  fun getNextBowler(): String
  fun getNextBall(): Double
  fun updateOverCount(): Double
  fun getRunForDelivery(): Run
  fun cancelDeliveryDueToNoBall()
  fun getRunValue(run: Run): Int
  fun getRemainingBatsmenCount(): Int
  fun getRemainingBallsInCurrentOverCount(): Double
  fun getRemainingOversCount(): Int
  fun incrementBatsmanScore(player: String, runs: Int)
  fun getBatsmanScore(player: String): Int
}

const val lowestScorePossible = 30
const val highestScorePossible = 90

class RepositoryImpl : Repository {

  private val batsmanStack = Stack<String>()
  private val bowlersList = arrayListOf(
    Pair(1, "B1"),
    Pair(2, "B2"),
    Pair(3, "B3"),
    Pair(4, "B4"),
    Pair(5, "B5")
  )
  private var previousBowler = -1
  private var runsConceeded = 0
  private var overCount = 0.0
  private val runsMap = hashMapOf(
    Pair(ONE, 1),
    Pair(TWO, 2),
    Pair(THREE, 3),
    Pair(FOUR, 4),
    Pair(SIX, 6),
    Pair(WICKET, 0),
    Pair(NO_BALL, 1)
  )
  private var wicketsLost = 0
  private val batsmanScoreMap = hashMapOf<String, Int>()

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
    runsConceeded = 0
    return bowlerName
  }

  override fun getNextBall(): Double {
    var ball = (overCount * 10).toInt()
    ball += 1
    overCount = ball.toFloat() / 10.0
    return overCount
  }

  override fun updateOverCount(): Double {
    overCount += 0.4
    return overCount
  }

  override fun getRunForDelivery(): Run {
    val run = when (Random().nextInt(6)) {
      0 -> ONE
      1 -> TWO
      2 -> THREE
      3 -> FOUR
      4 -> SIX
      5 -> WICKET
      else -> NO_BALL
    }
    runsConceeded += runsMap[run]!!
    return run
  }

  override fun cancelDeliveryDueToNoBall() {
    var ball = (overCount * 10).toInt()
    ball -= 1
    overCount = ball.toFloat() / 10.0
  }

  override fun getRunValue(run: Run): Int {
    return runsMap[run]!!
  }

  override fun getRemainingBatsmenCount(): Int {
    return batsmanStack.size
  }

  override fun getRemainingBallsInCurrentOverCount(): Double {
    return 0.6 - overCount % 1.0
  }

  override fun getRemainingOversCount(): Int {
    return (5 - overCount).toInt()
  }

  override fun incrementBatsmanScore(player: String, runs: Int) {
    if (batsmanScoreMap.containsKey(player)) {
      batsmanScoreMap[player] = batsmanScoreMap[player]!!.plus(runs)
    } else {
      batsmanScoreMap[player] = runs
    }
  }

  override fun getBatsmanScore(player: String): Int {
    if (batsmanScoreMap.containsKey(player)) {
      return batsmanScoreMap[player]!!
    } else {
      return 0
    }
  }

  private fun getRandomBowler(): String {
    println(bowlersList.toString())
    var randomValue = 0
    if (bowlersList.size != 1) {
      randomValue = Random().nextInt(bowlersList.size - 1)
    }
    previousBowler = bowlersList[randomValue].first
    val name = bowlersList[randomValue].second
    bowlersList.removeAt(randomValue)
    return name
  }

  private fun getRandomHigherOrderBowler(): String {
    val betterBowlersList = arrayListOf<Triple<Int, Int, String>>()
    bowlersList.forEachIndexed { index, pair ->
      if (pair.first > previousBowler) {
        betterBowlersList.add(Triple(index, pair.first, pair.second))
      }
    }
    if (betterBowlersList.size == 0) {
      return getRandomBowler()
    } else if (betterBowlersList.size == 1) {
      with(betterBowlersList[0]) {
        bowlersList.removeAt(first)
        previousBowler = second
        return third
      }
    } else {
      with(betterBowlersList[Random().nextInt(betterBowlersList.size - 1)]) {
        bowlersList.removeAt(first)
        previousBowler = second
        return third
      }
    }
  }
}

enum class Run {
  ONE, TWO, THREE, FOUR, SIX, WICKET, NO_BALL
}