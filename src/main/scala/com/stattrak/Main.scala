package com.stattrak

import com.stattrak.clients.DiscordClient
import com.stattrak.db.ScyllaDbClient
import com.stattrak.scheduler.Scheduler
import com.stattrak.store.UserStore

object Main {
  def main(args: Array[String]): Unit = {
    ScyllaDbClient()
    UserStore()
    DiscordClient()
    Scheduler()
  }
}
