package com.stattrak
package subscriber

import com.stattrak.api.ValorantApi.isUserValid
import com.stattrak.models.{User, Userdata}
import com.stattrak.store.UserStore
import com.stattrak.utils.Logging

class CommandHandler extends Logging {

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

  def list: List[User] = {
    try {
      UserStore.listUsers
    } catch {
      case ex: Exception =>
        error("Failed to list users", ex)
        List.empty
    }
  }
}

object SubscriberStatus extends Enumeration {
  type status = Value

  val successful: SubscriberStatus.Value = Value("successful")
  val invalid: SubscriberStatus.Value = Value("invalid")
  val alreadyExists: SubscriberStatus.Value = Value("alreadyExists")
  val failed: SubscriberStatus.Value = Value("failed")
}
