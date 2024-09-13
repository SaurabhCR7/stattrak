package com.stattrak.utils

import java.util.concurrent.atomic.AtomicLong

class Throttler(quietPeriod: Long) {
  private val nextRunTime = new AtomicLong(System.currentTimeMillis())
  
  def throttle(fn: => Unit): Unit = {
    val currentTime = System.currentTimeMillis()
    val updatedRunTime = nextRunTime.get() + quietPeriod
    if (currentTime > updatedRunTime) {
      nextRunTime.set(updatedRunTime)
      fn
    }
  }
}
