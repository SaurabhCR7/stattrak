package com.stattrak
package subscriber

import models.User
import utils.Logging

object CommandHandler extends Logging {

  def getCommand(msg: String): CommandType.Value = {
    val command = msg.split(" ")(0)
    command match {
      case ">subscribe" => CommandType.subscribe
      case ">unsubscribe" => CommandType.unsubscribe
      case _ => CommandType.invalid
    }
  }

  def getUser(msg: String): User = {
    try {
      val indexOfFirstSpace = msg.indexOf(" ")
      if (indexOfFirstSpace == -1) {
        User("Invalid", "Invalid")
      } else {
        val usernameWithTag = msg.substring(indexOfFirstSpace + 1)
        val hashCount = usernameWithTag.count(ch => ch == '#')
        if (hashCount == 1) {
          val arr = usernameWithTag.split("#")
          val username = arr(0).replace(" ", "%20")
          val tag = arr(1)
          info(s"User entered $username#$tag")
          User(username, tag)
        } else {
          User("Invalid", "Invalid")
        }
      }
    } catch {
      case ex: Exception => error("Invalid user entered", ex)
        User("Invalid", "Invalid")
    }
  }
}

object CommandType extends Enumeration {
  type command = Value

  val subscribe: CommandType.Value = Value(">subscribe")
  val unsubscribe: CommandType.Value = Value(">unsubscribe")
  val invalid: CommandType.Value = Value("")
}