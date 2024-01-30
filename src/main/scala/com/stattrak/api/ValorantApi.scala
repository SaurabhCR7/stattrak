package com.stattrak
package api

import api.dto.{MatchResponse, PatchResponse, RankResponse}
import models.User
import utils.Logging

import io.circe.parser

import scala.io.Source

object ValorantApi extends Logging {
  // This is to avoid exceeding api rate limits
  private val delayInMs = 200

  def fetchLastMatch(user: User): MatchResponse = try {
    val url = getCompetitiveApiUrl(user)
    Thread.sleep(delayInMs)
    val source = Source.fromURL(url)
    val jsonString = source.mkString
    val response = parseJsonResponse[MatchResponse](jsonString)
    source.close()
    response
  } catch {
    case e: Exception =>
      error("Failed to fetch match data from api with error : ", e)
      null
  }

  def fetchRank(user: User): RankResponse = try {
    val url = getRankApiUrl(user)
    Thread.sleep(delayInMs)
    val source = Source.fromURL(url)
    val jsonString = source.mkString
    val response = parseJsonResponse[RankResponse](jsonString)
    source.close()
    response
  } catch {
    case e: Exception =>
      error("Failed to fetch rank data from api with error : ", e)
      null
  }

  def isUserValid(user: User): Boolean = try {
    info(s"User is valid : $user")
    val url = getRankApiUrl(user)
    val source = Source.fromURL(url)
    info(s"Source : $source")
    val jsonString = source.mkString
    info(s"Json String $jsonString")
    val response = parseJsonResponse[RankResponse](jsonString)
    source.close()
    info(s"Response $response")
    response.status == 200
  } catch {
    case e: Exception => 
      error(s"Error while validating user $user", e)
      false
  }

  def fetchPatch: PatchResponse = try {
    val url = getPatchApiUrl
    Thread.sleep(delayInMs)
    val source = Source.fromURL(url)
    val jsonString = source.mkString
    val response = parseJsonResponse[PatchResponse](jsonString)
    source.close()
    response
  } catch {
    case e: Exception =>
      error("Failed to fetch patch notes from api with error : ", e)
      null
  }

  private def parseJsonResponse[T](jsonString: String): T = {
    parser.parse(jsonString) match {
      case Left(error) => throw new IllegalArgumentException(s"Invalid JSON object: ${error.message}")
      case Right(json) => json.asInstanceOf[T]
    }
  }

  // API Endpoints
  private def getRankApiUrl(user: User) = s"https://api.henrikdev.xyz/valorant/v1/mmr/ap/${user.username}/${user.tag}"

  private def getCompetitiveApiUrl(user: User) = "https://api.henrikdev.xyz/valorant/v1/lifetime/matches/ap/"
    + user.username + "/" + user.tag + "?size=1&mode=competitive"

  private val getPatchApiUrl = "https://api.henrikdev.xyz/valorant/v1/website/en-us?filter=game_updates"
}
