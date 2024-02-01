package com.stattrak.updaters

import com.stattrak.api.ValorantApi
import com.stattrak.api.dto.{RankDto, RankResponse}
import com.stattrak.clients.DiscordClient
import com.stattrak.models.{User, Userdata}
import com.stattrak.store.UserStore
import com.stattrak.utils.{DiscordMsgGenerator, Logging}

class RankUpdater extends Updater with Logging {
  def checkForUpdate(): Unit = {
    UserStore.cache.forEach((user, userdata) => {
      try {
        val recentRankData = getRecentRank(user)
        if (recentRankData.rank != userdata.rank) {
          val newUserdata = Userdata(userdata.channelId, userdata.matchId, recentRankData.rank)
          info(s"Rank update for $user, rank data : $recentRankData")
          updateRank(user, userdata, recentRankData)
          UserStore.updateRank(user, newUserdata)
        }
      } catch {
        case ex: Exception =>
          error(s"Removing user $user from database as the user is no longer valid", ex)
          UserStore.remove(user)
          DiscordClient.handleUserExpired(userdata.channelId, user)
      }
    })
  }
  
  private def getRecentRank(user: User): RankDto = {
    val response = ValorantApi.fetchRank(user)
    try {
      toRankDTO(response)
    } catch {
      case ex: Exception =>
        error(s"Error while parsing rankDTO for $user with response $response", ex)
        throw ex
    }
  }
  
  private def updateRank(user: User, userdata: Userdata, rankDto: RankDto): Unit = {
    val msg = DiscordMsgGenerator.getRankUpdateMsg(userdata.channelId, user, rankDto)
    DiscordClient.sendMessage(msg)
  }
  
  private def toRankDTO(response: RankResponse): RankDto = {
    RankDto(response.data.currenttierpatched, response.data.images.small, 
      response.data.elo, response.data.mmr_change_to_last_game)
  }
}
