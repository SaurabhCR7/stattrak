package com.stattrak
package utils

import com.stattrak.api.dto.MatchDto
import com.stattrak.models.{DiscordEmbedMessage, User}
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

  def getUserExpiredMsg(channelId: Long, user: User): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.red)
      .setTitle(s"User $user has expired !!")
      .setDescription(formatQuotedMsg("Subscribe again with the latest username and tag"))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getMatchUpdateMsg(channelId: Long, user: User, matchDto: MatchDto): DiscordEmbedMessage = {
    val color = if (matchDto.result == "Won") {
      Color.cyan
    } else {
      Color.red
    }
    val messageEmbed = new EmbedBuilder().setColor(color)
      .setTitle(matchDto.map)
      .setAuthor(user.toString)
      .setDescription(formatMatchMsg(matchDto))
      .setThumbnail(valorantThumbnail)
      .setImage(getMapThumbnail(matchDto.map))
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  private def formatQuotedMsg(msg: String) = "> " + msg

  private def formatMatchMsg(matchDto: MatchDto) = {
    s"**Server** : `${matchDto.server}` \n " +
      s"**Agent** : `${matchDto.agent}` \n " +
      s"**Score** : `${matchDto.myTeamScore}` : `${matchDto.enemyTeamScore}` \n " +
      s"**K** : `${matchDto.kills}`  |  **D** : `${matchDto.deaths}`  |  **A** : `${matchDto.assists}` \n " +
      s"**HS%** : `${matchDto.headshotPct}` \n " +
      s"**ADR** : `${matchDto.avgDamagePerRound}` \n"
  }

  private def getMapThumbnail(map: String) = {
    map match {
      case "Bind" => bindImg
      case "Split" => splitImg
      case "Ascent" => ascentImg
      case "Icebox" => iceboxImg
      case "Haven" => havenImg
      case "Breeze" => breezeImg
      case "Fracture" => fractureImg
      case "Pearl" => pearlImg
      case "Lotus" => lotusImg
      case _ => defaultMapImg
    }
  }
  
  // Images
  private val valorantThumbnail = "https://c.tenor.com/wuYSt-pgcoEAAAAM/valorant-games.gif"

  private val bindImg = "https://static.wikia.nocookie.net/valorant/images/2/23/Loading_Screen_Bind.png/revision/latest"
  private val splitImg = "https://static.wikia.nocookie.net/valorant/images/d/d6/Loading_Screen_Split.png/revision/latest"
  private val ascentImg = "https://static.wikia.nocookie.net/valorant/images/e/e7/Loading_Screen_Ascent.png/revision/latest"
  private val iceboxImg = "https://static.wikia.nocookie.net/valorant/images/1/13/Loading_Screen_Icebox.png/revision/latest"
  private val havenImg = "https://static.wikia.nocookie.net/valorant/images/7/70/Loading_Screen_Haven.png/revision/latest"
  private val breezeImg = "https://static.wikia.nocookie.net/valorant/images/1/10/Loading_Screen_Breeze.png/revision/latest"
  private val fractureImg = "https://static.wikia.nocookie.net/valorant/images/f/fc/Loading_Screen_Fracture.png/revision/latest"
  private val pearlImg = "https://static.wikia.nocookie.net/valorant/images/a/af/Loading_Screen_Pearl.png/revision/latest"
  private val lotusImg = "https://static.wikia.nocookie.net/valorant/images/d/d0/Loading_Screen_Lotus.png/revision/latest"
  private val defaultMapImg = "https://images2.alphacoders.com/132/1322753.jpeg"
}
