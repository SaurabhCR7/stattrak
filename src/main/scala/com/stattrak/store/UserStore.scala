package com.stattrak
package store

import com.stattrak.db.ScyllaDbClient
import com.stattrak.models.{User, Userdata}
import com.stattrak.utils.Logging

import java.util.concurrent.ConcurrentHashMap

object UserStore extends Logging {
  private val cacheSize = 1000
  val cache = new ConcurrentHashMap[User, Userdata]()
  
  def apply(): Unit = {
    info("Started store warmup")
    ScyllaDbClient.getAllUsersWithData.map {
      case (user, userdata) => cache.put(user, userdata)
    }
    info(s"Finished warming up store with size ${cache.size()}")
  }

  def add(user: User, userdata: Userdata): Unit = {
    ScyllaDbClient.addUser(user, userdata)
    cache.put(user, userdata)
  }

  def remove(user: User): Unit = {
    ScyllaDbClient.removeUser(user)
    cache.remove(user)
  }
  
  def isPresent(user: User): Boolean = {
    cache.containsKey(user)
  }
  
  def updateMatchId(user: User, userdata: Userdata): Unit = {
    ScyllaDbClient.updateMatchId(user, userdata.matchId)
    cache.put(user, userdata)
  }
  
  def updateRank(user: User, userdata: Userdata): Unit = {
    ScyllaDbClient.updateRank(user, userdata.rank)
    cache.put(user, userdata)
  }
}
