package com.stattrak
package api.dto

case class MatchDto(matchid: String, map: String, result: String, server: String, myTeamScore: Int,
                    enemyTeamScore: Int, level: Int, agent: String, kills: Int, deaths: Int,
                    assists: Int, kdRatio: Float, headshotPct: Int, avgDamagePerRound: Int)

case class MatchResponse(status: Int, results: MatchResults, data: Array[MatchData])


case class MatchData(meta: MatchMeta, stats: MatchStats, teams: Teams)

case class MatchResults(total: Int, returned: Int, before: Int, after: Int)

case class MatchStats(puuid: String, team: String, level: Int, character: Character, tier: Int,
                      score: Int, kills: Int, deaths: Int, assists: Int, shots: Shots, damage: Damage)

case class MatchMeta(id: String, map: Map, version: String, mode: String, started_at: String, season: Season,
                     region: String, cluster: String)