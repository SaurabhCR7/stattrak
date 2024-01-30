package com.stattrak
package api.dto

case class RankDto(rank: String, imageUrl: String, elo: Int, mmrChange: Int)

case class RankResponse(status: Int, data: RankData)

case class RankData(currenttier: Int, currenttierpatched: String, images: ImageUrls, ranking_in_tier: Int, 
                    mmr_change_to_last_game: Int, elo: Int, name: String, tag: String, old: Boolean)
