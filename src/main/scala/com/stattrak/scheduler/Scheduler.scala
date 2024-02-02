package com.stattrak.scheduler

import com.stattrak.updaters.{MatchUpdater, PatchUpdater, RankUpdater, Updater}

import java.util.concurrent.{ScheduledThreadPoolExecutor, TimeUnit}

object Scheduler {
  private val threadpoolSize = 3 // Equal to the number of updaters
  private val threadpool = new ScheduledThreadPoolExecutor(threadpoolSize)

  // Properties :
  private val initialDelay = 0 // 1 min
  private val matchUpdaterInterval = 1 // 1 min
  private val rankUpdaterInterval = 1 // 1 mins
  private val patchUpdaterInterval = 1 // 30 mins
  
  private val matchUpdater = new MatchUpdater
  private val rankUpdater = new RankUpdater
  private val patchUpdater = new PatchUpdater
  
  def apply(): Unit = {
    threadpool.scheduleAtFixedRate(() => matchUpdater.checkForUpdate(),
      initialDelay, matchUpdaterInterval, TimeUnit.MINUTES)

    threadpool.scheduleAtFixedRate(() => rankUpdater.checkForUpdate(),
      initialDelay, rankUpdaterInterval, TimeUnit.MINUTES)

    threadpool.scheduleAtFixedRate(() => patchUpdater.checkForUpdate(),
      initialDelay, patchUpdaterInterval, TimeUnit.MINUTES)
  }
}
