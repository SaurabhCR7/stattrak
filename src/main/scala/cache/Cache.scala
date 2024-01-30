package com.stattrak
package cache

import db.ScyllaDbClient
import models.{User, Userdata}
import utils.Logging

import java.util.concurrent.ConcurrentHashMap

object Cache extends Logging {
  private val cacheSize = 1000
  private val cache = new ConcurrentHashMap[User, Userdata]()
  
  def apply(): Unit = {
    info("Started cache warmup")
    ScyllaDbClient.getAllUsersWithData.map {
      case (user, userdata) => cache.put(user, userdata)
    }
    info(s"Finished warming up cache with size ${cache.size()}")
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
    cache.contains(user)
  }

}
