package com.stattrak.utils

object Assets {
  def getMapThumbnail(map: String): String = {
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
      case "Sunset" => sunsetImg
      case "Abyss" => abyssImg
      case _ => defaultMapImg
    }
  }

  def getAgentImg(agent: String): String = {
    agent match {
      case "Brimstone" => brimstone
      case "Viper" => viper
      case "Omen" => omen
      case "Cypher" => cypher
      case "Sova" => sova
      case "Sage" => sage
      case "Phoenix" => phoenix
      case "Jett" => jett
      case "Raze" => raze
      case "Breach" => breach
      case "Reyna" => reyna
      case "Killjoy" => killjoy
      case "Skye" => skye
      case "Yoru" => yoru
      case "Astra" => astra
      case "Kayo" => kayo
      case "Chamber" => chamber
      case "Neon" => neon
      case "Fade" => fade
      case "Harbor" => harbor
      case "Gekko" => gekko
      case "Deadlock" => deadlock
      case "Iso" => iso
      case "Clove" => clove
      case "Vyse" => vyse
      case _ => valorantThumbnail
    }
  }
  
  // <------------------- Images ------------------->

  // Source : https://valorant.fandom.com/

  // Common
  val valorantThumbnail = "https://c.tenor.com/wuYSt-pgcoEAAAAM/valorant-games.gif"
  private val defaultMapImg = "https://images2.alphacoders.com/132/1322753.jpeg"

  // Maps
  private val bindImg = "https://static.wikia.nocookie.net/valorant/images/2/23/Loading_Screen_Bind.png"
  private val splitImg = "https://static.wikia.nocookie.net/valorant/images/d/d6/Loading_Screen_Split.png"
  private val iceboxImg = "https://static.wikia.nocookie.net/valorant/images/1/13/Loading_Screen_Icebox.png"
  private val breezeImg = "https://static.wikia.nocookie.net/valorant/images/1/10/Loading_Screen_Breeze.png"
  private val fractureImg = "https://static.wikia.nocookie.net/valorant/images/f/fc/Loading_Screen_Fracture.png"
  private val pearlImg = "https://static.wikia.nocookie.net/valorant/images/a/af/Loading_Screen_Pearl.png"
  private val havenImg = "https://static.wikia.nocookie.net/valorant/images/7/70/Loading_Screen_Haven.png"
  private val lotusImg = "https://static.wikia.nocookie.net/valorant/images/d/d0/Loading_Screen_Lotus.png"
  private val ascentImg = "https://static.wikia.nocookie.net/valorant/images/e/e7/Loading_Screen_Ascent.png"
  private val sunsetImg = "https://static.wikia.nocookie.net/valorant/images/5/5c/Loading_Screen_Sunset.png"
  private val abyssImg = "https://static.wikia.nocookie.net/valorant/images/6/61/Loading_Screen_Abyss.png"
  // Agents
  private val brimstone = "https://static.wikia.nocookie.net/valorant/images/4/4d/Brimstone_icon.png"
  private val viper = "https://static.wikia.nocookie.net/valorant/images/5/5f/Viper_icon.png"
  private val omen = "https://static.wikia.nocookie.net/valorant/images/b/b0/Omen_icon.png"
  private val cypher = "https://static.wikia.nocookie.net/valorant/images/8/88/Cypher_icon.png"
  private val sova = "https://static.wikia.nocookie.net/valorant/images/4/49/Sova_icon.png"
  private val sage = "https://static.wikia.nocookie.net/valorant/images/7/74/Sage_icon.png"
  private val phoenix = "https://static.wikia.nocookie.net/valorant/images/1/14/Phoenix_icon.png"
  private val jett = "https://static.wikia.nocookie.net/valorant/images/3/35/Jett_icon.png"
  private val raze = "https://static.wikia.nocookie.net/valorant/images/9/9c/Raze_icon.png"
  private val breach = "https://static.wikia.nocookie.net/valorant/images/5/53/Breach_icon.png"
  private val reyna = "https://static.wikia.nocookie.net/valorant/images/b/b0/Reyna_icon.png"
  private val killjoy = "https://static.wikia.nocookie.net/valorant/images/1/15/Killjoy_icon.png"
  private val skye = "https://static.wikia.nocookie.net/valorant/images/3/33/Skye_icon.png"
  private val yoru = "https://static.wikia.nocookie.net/valorant/images/d/d4/Yoru_icon.png"
  private val astra = "https://static.wikia.nocookie.net/valorant/images/0/08/Astra_icon.png"
  private val kayo = "https://static.wikia.nocookie.net/valorant/images/f/f0/KAYO_icon.png"
  private val chamber = "https://static.wikia.nocookie.net/valorant/images/0/09/Chamber_icon.png"
  private val neon = "https://static.wikia.nocookie.net/valorant/images/d/d0/Neon_icon.png"
  private val fade = "https://static.wikia.nocookie.net/valorant/images/a/a6/Fade_icon.png"
  private val harbor = "https://static.wikia.nocookie.net/valorant/images/f/f3/Harbor_icon.png"
  private val gekko = "https://static.wikia.nocookie.net/valorant/images/6/66/Gekko_icon.png"
  private val deadlock = "https://static.wikia.nocookie.net/valorant/images/e/eb/Deadlock_icon.png"
  private val iso = "https://static.wikia.nocookie.net/valorant/images/b/b7/Iso_icon.png"
  private val clove = "https://static.wikia.nocookie.net/valorant/images/3/30/Clove_icon.png"
  private val vyse = "https://static.wikia.nocookie.net/valorant/images/2/21/Vyse_icon.png"
}
