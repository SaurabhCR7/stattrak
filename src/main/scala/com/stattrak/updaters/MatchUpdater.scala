package com.stattrak.updaters

import com.stattrak.api.ValorantApi
import com.stattrak.api.dto.{MatchDto, MatchResponse}
import com.stattrak.clients.DiscordClient
import com.stattrak.models.{User, Userdata}
import com.stattrak.store.UserStore
import com.stattrak.utils.{DiscordMsgGenerator, Logging}

class MatchUpdater extends Updater with Logging {
  def checkForUpdate(): Unit = {
    UserStore.cache.forEach((user, userdata) => {
      try {
        val recentMatchData = getRecentMatch(user)
        if (recentMatchData.matchid != userdata.matchId) {
          val newUserdata = Userdata(userdata.channelId, recentMatchData.matchid, userdata.rank)
          info(s"Latest match update for $user, match data : $recentMatchData")
          updateUser(user, userdata, recentMatchData)
          UserStore.updateMatchId(user, newUserdata)
        }
      } catch {
        case ex: Exception => 
          error(s"Removing user $user from database as the user is no longer valid", ex)
          UserStore.remove(user)
          DiscordClient.handleUserExpired(userdata.channelId, user)
      }
    })
  }

  private def getRecentMatch(user: User): MatchDto = {
    val response = ValorantApi.fetchLastMatch(user)
    try {
      toMatchDTO(response)
    } catch {
      case ex: Exception =>
        error(s"Error while parsing matchDTO for $user with response $response", ex)
        throw ex
    }
  }

  private def updateUser(user: User, userdata: Userdata, matchDto: MatchDto): Unit = {
    val msg = DiscordMsgGenerator.getMatchUpdateMsg(userdata.channelId, user, matchDto)
    DiscordClient.sendMessage(msg)
  }
  
  private def toMatchDTO(response: MatchResponse): MatchDto = {
    val meta = response.data(0).meta
    val stats = response.data(0).stats

    val redTeamScore = response.data(0).teams.red
    val blueTeamScore = response.data(0).teams.blue

    val matchid = meta.id
    val map = meta.map.name
    val team = stats.team

    val result = getResult(team, redTeamScore, blueTeamScore)
    val server = meta.cluster
    val myTeamScore = getMyTeamScore(team, redTeamScore, blueTeamScore)
    val enemyTeamScore = getEnemyTeamScore(team, redTeamScore, blueTeamScore)
    val level = stats.level
    val agent = stats.character.name
    val kills = stats.kills
    val deaths = stats.deaths
    val assists = stats.assists
    val headshotPct = getHeadshotPct(stats.shots.head, stats.shots.body, stats.shots.leg)
    val avgDamagePerRound = getAvgDamagePerRound(stats.damage.made, myTeamScore, enemyTeamScore)

    MatchDto(matchid, map, result, server, myTeamScore, enemyTeamScore,
      level, agent, kills, deaths, assists, headshotPct, avgDamagePerRound)
  }

  private def getResult(team: String, redTeamScore: Int, blueTeamScore: Int): String = {
    if (team == "Red" && redTeamScore > blueTeamScore) return "Won"
    if (team == "Blue" && blueTeamScore > redTeamScore) return "Won"
    "Lost"
  }

  private def getMyTeamScore(team: String, redTeamScore: Int, blueTeamScore: Int) = {
    if (team == "Red") redTeamScore
    else blueTeamScore
  }

  private def getEnemyTeamScore(team: String, redTeamScore: Int, blueTeamScore: Int) = {
    if (team == "Red") blueTeamScore
    else redTeamScore
  }

  private def getHeadshotPct(head: Int, body: Int, leg: Int) = {
    val total = head + body + leg
    if (total == 0) 0
    else (head * 100) / total
  }

  private def getAvgDamagePerRound(damage: Int, myTeamScore: Int, enemyTeamScore: Int) = {
    val totalRounds = myTeamScore + enemyTeamScore
    if (totalRounds == 0) {
      0
    } else {
      damage / totalRounds
    }
  }
}
