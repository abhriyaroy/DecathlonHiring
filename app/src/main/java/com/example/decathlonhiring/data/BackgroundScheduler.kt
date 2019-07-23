package com.example.decathlonhiring.data

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

interface BackgroundScheduler {
  fun getComputationScheduler(): Scheduler
}

class BackgroundSchedulerImpl : BackgroundScheduler {
  override fun getComputationScheduler() = Schedulers.computation()
}