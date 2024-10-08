package com.stattrak
package clients

import com.stattrak.models.{DiscordEmbedMessage, User, Userdata}
import com.stattrak.subscriber.{CommandHandler, CommandType, CommandValidator, SubscriberStatus}
import com.stattrak.utils.DiscordMsgGenerator.*
import com.stattrak.utils.{DiscordMsgGenerator, Logging}
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.{JDA, JDABuilder}

import scala.io.Source

object DiscordClient extends ListenerAdapter with Logging {
  private val source = Source.fromFile("token.env")
  private val discordToken = source.getLines().next()
  source.close()

  private val jda = createJDA()
  private val commandHandler = new CommandHandler

  def apply(): Unit = {
    jda.addEventListener(this)
    info("Discord Client Initialized")
  }

  override def onMessageReceived(event: MessageReceivedEvent): Unit = {
    if (event.getAuthor.isBot) return

    val message = event.getMessage.getContentDisplay
    if (!message.startsWith(">")) return
    val channelId = event.getChannel.getIdLong

    val command = CommandValidator.getCommand(message)
    if (command == CommandType.invalid) {
      handleInvalidCommand(channelId, message)
      return
    }

    val user = CommandValidator.getUser(message)

    if (user.username == "Invalid") {
      handleInvalidUser(channelId, user)
      return
    }

    val userdata = Userdata(channelId, "Empty", "Empty")

    command match {
      case CommandType.subscribe => {
        val result = commandHandler.subscribe(user, userdata)
        result match {
          case SubscriberStatus.invalid => handleInvalidUser(channelId, user)
          case SubscriberStatus.failed => handleFailure(channelId, message)
          case SubscriberStatus.alreadyExists => handleAlreadyExistsUser(channelId, user)
          case SubscriberStatus.successful => handleUserSubscribed(channelId, user)
        }
      }
      case CommandType.unsubscribe => {
        commandHandler.unsubscribe(user)
        handleUserUnsubscribed(channelId, user)
      }
      case CommandType.list => {
        val users = commandHandler.list
        sendMessage(getListUsersMsg(channelId, users))
      }
      case CommandType.invalid => handleInvalidCommand(channelId, message)
    }
  }

  def sendMessage(msg: DiscordEmbedMessage): Unit = {
    val channel = jda.getChannelById(classOf[MessageChannel], msg.channelId)
    channel.sendMessageEmbeds(msg.messageEmbed).queue()
  }

  private def handleInvalidCommand(channelId: Long, message: String): Unit = {
    error(s"Invalid command found : $message")
    val msg = getInvalidCommandMsg(channelId, message)
    sendMessage(msg)
  }

  private def handleFailure(channelId: Long, message: String): Unit = {
    error(s"Failed while executing command : $message")
    val msg = getFailureMsg(channelId, message)
    sendMessage(msg)
  }

  private def handleInvalidUser(channelId: Long, user: User): Unit = {
    error(s"Invalid user found : $user")
    val msg = getInvalidUserMsg(channelId, user.toString)
    sendMessage(msg)
  }

  private def handleAlreadyExistsUser(channelId: Long, user: User): Unit = {
    warn(s"User already exists : $user")
    val msg = getUserAlreadyExistsMsg(channelId, user.toString)
    sendMessage(msg)
  }

  private def handleUserSubscribed(channelId: Long, user: User): Unit = {
    info(s"User is successfully subscribed  : $user")
    val msg = getUserSubscribedMsg(channelId, user.toString)
    sendMessage(msg)
  }

  private def handleUserUnsubscribed(channelId: Long, user: User): Unit = {
    info(s"User is successfully unsubscribed  : $user")
    val msg = getUserUnsubscribedMsg(channelId, user.toString)
    sendMessage(msg)
  }
  
  def handleUserExpired(channelId: Long, user: User): Unit = {
    val msg = getUserExpiredMsg(channelId, user)
    sendMessage(msg)
  }

  private def createJDA(): JDA = {
    JDABuilder.createDefault(discordToken)
      .enableIntents(GatewayIntent.MESSAGE_CONTENT).build()
  }
}
