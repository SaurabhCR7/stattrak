package com.stattrak
package utils

import models.DiscordEmbedMessage

import net.dv8tion.jda.api.EmbedBuilder

import java.awt.Color

object DiscordMsgGenerator {
  def getInvalidCommandMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.red)
      .setTitle("Invalid Command !!")
      .setDescription(formatQuotedMsg(msg))
      .setThumbnail(valorantThumbnail)
      .build()
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getInvalidUserMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.red)
      .setTitle("Username or tag is not valid !!")
      .setDescription(formatQuotedMsg(msg))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getFailureMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.red)
      .setTitle("Failed while executing command !!")
      .setDescription(formatQuotedMsg(msg))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getUserAlreadyExistsMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.orange)
      .setTitle("User is already subscribed !!")
      .setDescription(formatQuotedMsg(msg))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getUserSubscribedMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.cyan)
      .setTitle("User is successfully subscribed !!")
      .setDescription(formatQuotedMsg(msg))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  private def formatQuotedMsg(msg: String) = "> " + msg
  
  // Images
  private val valorantThumbnail = "https://c.tenor.com/wuYSt-pgcoEAAAAM/valorant-games.gif"
}
