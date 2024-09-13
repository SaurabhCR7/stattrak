package com.stattrak
package utils

import com.stattrak.api.dto.{MatchDto, PatchDto, RankDto}
import com.stattrak.models.{DiscordEmbedMessage, User}
import com.stattrak.utils.Assets.*
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

  def getUserUnsubscribedMsg(channelId: Long, msg: String): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder().setColor(Color.red)
      .setTitle("User is successfully unsubscribed !!")
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
    val color = matchDto.result match {
      case "Won" => Color.cyan
      case "Lost" => Color.red
      case "Draw" => Color.white
      case _ => Color.white
    }
    val messageEmbed = new EmbedBuilder().setColor(color)
      .setTitle(s"${matchDto.map} (${matchDto.result})")
      .setAuthor(user.toString)
      .setDescription(formatMatchMsg(matchDto))
      .setThumbnail(getAgentImg(matchDto.agent))
      .setImage(getMapThumbnail(matchDto.map))
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getRankUpdateMsg(channelId: Long, user: User, rankDto: RankDto): DiscordEmbedMessage = {
    val color = if (rankDto.mmrChange >= 0) {
      Color.cyan
    } else {
      Color.red
    }
    val messageEmbed = new EmbedBuilder().setColor(color)
      .setTitle(rankDto.rank)
      .setAuthor(user.toString)
      .setDescription(formatRankMsg(rankDto))
      .setThumbnail(rankDto.imageUrl)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getPatchMsg(channelId: Long, patchData: PatchDto): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder()
      .setAuthor(patchData.title)
      .setColor(Color.white)
      .setDescription(patchData.url)
      .setThumbnail(valorantThumbnail)
      .setImage(patchData.banner_url)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }

  def getListUsersMsg(channelId: Long, users: List[User]): DiscordEmbedMessage = {
    val messageEmbed = new EmbedBuilder()
      .setAuthor("Users")
      .setColor(Color.white)
      .setDescription(formatListUsersMsg(users))
      .setThumbnail(valorantThumbnail)
      .build
    DiscordEmbedMessage(channelId, messageEmbed)
  }


  private def formatQuotedMsg(msg: String) = "> " + msg

  private def formatMatchMsg(matchDto: MatchDto) = {
    val kd = f"${matchDto.kdRatio}%.2f"
    s"**Server** : `${matchDto.server}` \n " +
      s"**Agent** : `${matchDto.agent}` \n " +
      s"**Score** : `${matchDto.myTeamScore}` : `${matchDto.enemyTeamScore}` \n " +
      s"**K** : `${matchDto.kills}`  |  **D** : `${matchDto.deaths}`  |  **A** : `${matchDto.assists}` \n " +
      s"**K/D** : `$kd` \n " +
      s"**HS%** : `${matchDto.headshotPct}` \n " +
      s"**ADR** : `${matchDto.avgDamagePerRound}` \n"
  }

  private def formatRankMsg(rankDto: RankDto) = {
    val rankUpMsg = s"*Congratulations on your rank up! \n You've been grinding hard, and it's paying off. \n " +
      s"Keep up the good work!*"
    val derankMsg = s"*Hey, I saw you deranked. \n It's okay, everyone has bad days. \n " +
      s"Just keep your head up and keep practicing, \n and you'll be back to your old rank in no time.*"
    val greetingMsg = if (rankDto.mmrChange < 0) {
      derankMsg
    } else {
      rankUpMsg
    }
    s"$greetingMsg \n\n **Elo** : `${rankDto.elo}`\n"
  }
  
  private def formatListUsersMsg(users: List[User]) = {
    users.map(user => user.toString + "\n").toString()
  }
}
