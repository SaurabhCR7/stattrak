package com.stattrak.scheduler

import com.stattrak.updaters.{MatchUpdater, PatchUpdater, RankUpdater}

import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}

object Scheduler {
  private val matchUpdater = 1
  private val rankUpdater = 2
  private val patchUpdater = 3

  private val updaterFactory = Map(
    matchUpdater -> new MatchUpdater, 
    rankUpdater -> new RankUpdater, 
    patchUpdater -> new PatchUpdater
  )
  
  private val threadpoolSize = updaterFactory.size
  private val threadpool = new ScheduledThreadPoolExecutor(threadpoolSize)

  // Properties :
  private val min = 60 * 1000
  private val initialDelay = 1 * min
  private val matchUpdaterInterval = 1 * min
  private val rankUpdaterInterval = 1 * min
  private val patchUpdaterInterval = 15 * min
  

  def apply(): Unit = {
    threadpool.scheduleAtFixedRate(() => updaterFactory(matchUpdater).checkForUpdate(),
      initialDelay, matchUpdaterInterval, TimeUnit.MILLISECONDS)

    threadpool.scheduleAtFixedRate(() => updaterFactory(rankUpdater).checkForUpdate(),
      initialDelay, rankUpdaterInterval, TimeUnit.MILLISECONDS)

    threadpool.scheduleAtFixedRate(() => updaterFactory(patchUpdater).checkForUpdate(),
      initialDelay, patchUpdaterInterval, TimeUnit.MILLISECONDS)
  }
}
