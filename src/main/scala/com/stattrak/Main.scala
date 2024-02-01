package com.stattrak

import store.UserStore
import clients.DiscordClient
import com.stattrak.scheduler.Scheduler
import db.ScyllaDbClient

object Main {
  def main(args: Array[String]): Unit = {
    ScyllaDbClient()
    UserStore()
    DiscordClient()
    Scheduler()
  }
}
