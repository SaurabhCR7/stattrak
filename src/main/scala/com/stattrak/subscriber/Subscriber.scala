package com.stattrak
package subscriber

import api.ValorantApi.{getLatestMatchId, getLatestRank, isUserValid}
import store.UserStore
import models.{User, Userdata}
import utils.Logging

class Subscriber extends Logging {

  def subscribe(user: User, userdata: Userdata): SubscriberStatus.Value = {
    if (!isUserValid(user)) {
      SubscriberStatus.invalid
    } else if (UserStore.isPresent(user)) {
      SubscriberStatus.alreadyExists
    } else {
      try {
        val newUserData = Userdata(userdata.channelId, "Empty", "Empty")
        UserStore.add(user, newUserData)
        SubscriberStatus.successful
      } catch {
        case ex: Exception =>
          error(s"Failed to subscribe user $user", ex)
          SubscriberStatus.failed
      }
    }
  }

  def unsubscribe(user: User): Unit = {
    UserStore.remove(user)
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