package com.stattrak

import cache.Cache
import clients.DiscordClient
import db.ScyllaDbClient

object Main {
  def main(args: Array[String]): Unit = {
    ScyllaDbClient()
    Cache()
    DiscordClient()
  }
}