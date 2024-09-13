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
    synchronized {
      ScyllaDbClient.addUser(user, userdata)
      cache.put(user, userdata)
    }
  }

  def remove(user: User): Unit = {
    synchronized {
      ScyllaDbClient.removeUser(user)
      cache.remove(user)
    }
  }
  
  def isPresent(user: User): Boolean = {
    cache.containsKey(user)
  }
  
  def updateMatchId(user: User, newMatchId: String): Unit = {
    synchronized {
      ScyllaDbClient.updateMatchId(user, newMatchId)
      val userdata = cache.get(user).copy(matchId = newMatchId)
      cache.put(user, userdata)
    }
  }
  
  def updateRank(user: User, newRank: String): Unit = {
    synchronized {
      ScyllaDbClient.updateRank(user, newRank)
      val userdata = cache.get(user).copy(rank = newRank)
      cache.put(user, userdata)
    }
  }
  
  def listUsers: List[User] = {
    ScyllaDbClient.getAllUsers
  }
}
