package com.example.decathlonhiring.data

import io.reactivex.Single
import java.util.*

interface Repository {
  fun getTargetScore(): Single<Int>
}


const val lowestScorePossible = 30
const val highestScorePossible = 90

class RepositoryImpl(private val backgroundScheduler: BackgroundScheduler) : Repository {

  override fun getTargetScore(): Single<Int> {
    return Single.just(Random().nextInt(highestScorePossible - lowestScorePossible) + lowestScorePossible)
      .subscribeOn(backgroundScheduler.getComputationScheduler())
  }
}