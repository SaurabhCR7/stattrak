package com.stattrak
package api

import com.stattrak.api.dto.{MatchResponse, PatchResponse, RankResponse}
import com.stattrak.models.User
import com.stattrak.utils.Logging
import io.circe.*
import io.circe.generic.auto.*
import io.circe.parser.*

import scala.io.Source

object ValorantApi extends Logging {
  // This is to avoid exceeding api rate limits
  private val delayInMs = 200

  def fetchMatchData(user: User): MatchResponse = {
    try {
      val url = getCompetitiveApiUrl(user)
      Thread.sleep(delayInMs)
      val source = Source.fromURL(url)
      val jsonString = source.mkString
      val response = parseJson[MatchResponse](jsonString)
      source.close()
      response
    } catch {
      case e: Exception =>
        error("Failed to fetch match data from api with error : ", e)
        null
    }
  }

  def fetchRankData(user: User): RankResponse = {
    try {
      val url = getRankApiUrl(user)
      Thread.sleep(delayInMs)
      val source = Source.fromURL(url)
      val jsonString = source.mkString
      val response = parseJson[RankResponse](jsonString)
      source.close()
      response
    } catch {
      case e: Exception =>
        error("Failed to fetch rank data from api with error : ", e)
        null
    }
  }

  def isUserValid(user: User): Boolean = {
    try {
      val url = getRankApiUrl(user)
      val source = Source.fromURL(url)
      val jsonString = source.mkString
      val response = parseJson[RankResponse](jsonString)
      source.close()
      response.status == 200
    } catch {
      case e: Exception =>
        error(s"Error while validating user $user", e)
        false
    }
  }

  def fetchPatchData: PatchResponse = {
    try {
      val url = getPatchApiUrl
      Thread.sleep(delayInMs)
      val source = Source.fromURL(url)
      val jsonString = source.mkString
      val response = parseJson[PatchResponse](jsonString)
      source.close()
      response
    } catch {
      case e: Exception =>
        error("Failed to fetch patch notes from api with error : ", e)
        null
    }
  }
  
  def getLatestRank(user: User): String = {
    try {
      val response = fetchRankData(user)
      response.data.currenttierpatched
    } catch {
      case ex: Exception => 
        error(s"Failed to fetch the latest rank for user $user")
        "Empty"
    }
  }

  def getLatestMatchId(user: User): String = {
    try {
      val response = fetchMatchData(user)
      response.data(0).meta.id
    } catch {
      case ex: Exception =>
        error(s"Failed to fetch the latest matchid for user $user")
        "Empty"
    }
  }

  private def parseJson[T: Decoder](jsonString: String): T = {
    decode[T](jsonString) match {
      case Left(error) => throw new IllegalArgumentException("Invalid json response")
      case Right(result) => result
    }
  }

  // API Endpoints
  private def getRankApiUrl(user: User) = s"https://api.henrikdev.xyz/valorant/v1/mmr/ap/${user.username}/${user.tag}"

  private def getCompetitiveApiUrl(user: User) = "https://api.henrikdev.xyz/valorant/v1/lifetime/matches/ap/"
    + user.username + "/" + user.tag + "?size=1&mode=competitive"

  private val getPatchApiUrl = "https://api.henrikdev.xyz/valorant/v1/website/en-us?filter=game_updates"
}
