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
  fun generateTargetScore(): String
  fun getTargetScore(): Int
  fun getCurrentScore(): Int
  fun getCurrentDeliveryScore(): Int
  fun getRequiredRunsToWin(): Int

  @Throws(OutOfBatsmenException::class)
  fun getNextBatsman(): String

  fun getNextBowler(): String
  fun getNextBall(): Double
  fun updateOverCount(): Double
  fun getRunForDelivery(): Run
  fun cancelDeliveryDueToNoBall()
  fun getRemainingBatsmenCount(): Int
  fun getRemainingBallsInCurrentOverCount(): Double
  fun getRemainingOversCount(): Int
  fun incrementBatsmanScore(player: String, runs: Run)
  fun getBatsmanScore(player: String): Int
  fun getWicketsLost(): Int
}

const val lowestScorePossible = 30
const val highestScorePossible = 90
private const val BOWLING_NOT_OPENED_STATE = -1
private const val OVER_COUNT_HELPER = 0.4
private const val NUMBER_OF_BALLS_IN_AN_OVER = 0.6
private const val MAXIMUM_NUMBER_OF_OVERS = 5

class RepositoryImpl : Repository {

  private var targetScore: Int = 0
  private var currentScore: Int = 0
  private var currentDeliveryScore: Int = 0
  private var requiredRuns: Int = 0
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
  private val batsmanScoreMap = hashMapOf<String, Int>()

  init {
    batsmanStack.push("P5")
    batsmanStack.push("P4")
    batsmanStack.push("P3")
    batsmanStack.push("P2")
    batsmanStack.push("P1")
  }

  override fun generateTargetScore(): String {
    targetScore = Random().nextInt(highestScorePossible - lowestScorePossible) + lowestScorePossible
    return targetScore.toString()
  }

  override fun getTargetScore(): Int {
    return targetScore
  }

  override fun getCurrentDeliveryScore(): Int {
    return currentDeliveryScore
  }

  override fun getCurrentScore(): Int {
    return currentScore
  }

  override fun getRequiredRunsToWin(): Int {
    requiredRuns = targetScore - currentScore
    return requiredRuns
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
    if (previousBowler == BOWLING_NOT_OPENED_STATE || runsConceeded < 10) {
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
    overCount += OVER_COUNT_HELPER
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
    currentDeliveryScore = runsMap[run]!!
    runsConceeded += currentDeliveryScore
    currentScore += currentDeliveryScore
    return run
  }

  override fun cancelDeliveryDueToNoBall() {
    var ball = (overCount * 10).toInt()
    ball -= 1
    overCount = ball.toFloat() / 10.0
  }

  override fun getRemainingBatsmenCount(): Int {
    return batsmanStack.size
  }

  override fun getRemainingBallsInCurrentOverCount(): Double {
    return NUMBER_OF_BALLS_IN_AN_OVER - overCount % 1.0
  }

  override fun getRemainingOversCount(): Int {
    return (MAXIMUM_NUMBER_OF_OVERS - overCount).toInt()
  }

  override fun incrementBatsmanScore(player: String, runs: Run) {
    if (batsmanScoreMap.containsKey(player)) {
      batsmanScoreMap[player] = batsmanScoreMap[player]!!.plus(runsMap[runs]!!)
    } else {
      batsmanScoreMap[player] = runsMap[runs]!!
    }
  }

  override fun getBatsmanScore(player: String): Int {
    if (batsmanScoreMap.containsKey(player)) {
      return batsmanScoreMap[player]!!
    } else {
      return 0
    }
  }

  override fun getWicketsLost(): Int {
    return MAXIMUM_NUMBER_OF_OVERS - (batsmanStack.size + 1)
  }

  private fun getRandomBowler(): String {
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