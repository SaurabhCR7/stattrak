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
          if (userdata.rank != "Empty") {
            notifyUser(user, userdata, recentRankData)
          }
          UserStore.updateRank(user, newUserdata)
        }
      } catch {
        case ex: Exception => error(s"Failed to fetch rank data for user $user", ex)
      }
    })
  }
  
  private def getRecentRank(user: User): RankDto = {
    val response = ValorantApi.fetchRankData(user)
    try {
      toRankDTO(response)
    } catch {
      case ex: Exception =>
        error(s"Error while parsing rankDTO for $user with response $response", ex)
        throw ex
    }
  }
  
  private def notifyUser(user: User, userdata: Userdata, rankDto: RankDto): Unit = {
    val msg = DiscordMsgGenerator.getRankUpdateMsg(userdata.channelId, user, rankDto)
    DiscordClient.sendMessage(msg)
  }
  
  private def toRankDTO(response: RankResponse): RankDto = {
    RankDto(response.data.currenttierpatched, response.data.images.small, 
      response.data.elo, response.data.mmr_change_to_last_game)
  }
}
