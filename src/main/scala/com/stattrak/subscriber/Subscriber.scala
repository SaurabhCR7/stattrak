package com.stattrak
package subscriber

import api.ValorantApi.isUserValid
import cache.Cache
import models.{User, Userdata}
import utils.Logging

class Subscriber extends Logging {

  def subscribe(user: User, userdata: Userdata): SubscriberStatus.Value = {
    if (!isUserValid(user)) {
      SubscriberStatus.invalid
    } else if (Cache.isPresent(user)) {
      SubscriberStatus.alreadyExists
    } else {
      try {
        Cache.add(user, userdata)
        SubscriberStatus.successful
      } catch {
        case ex: Exception =>
          error(s"Failed to subscribe user $user", ex)
          SubscriberStatus.failed
      }
    }
  }

  def unsubscribe(user: User): Unit = {
    Cache.remove(user)
    info(s"User $user unsubscribed Successfully")
  }
}

object SubscriberStatus extends Enumeration {
  type status = Value

  val successful: SubscriberStatus.Value = Value("successful")
  val invalid: SubscriberStatus.Value = Value("invalid")
  val alreadyExists: SubscriberStatus.Value = Value("alreadyExists")
  val failed: SubscriberStatus.Value = Value("failed")
}