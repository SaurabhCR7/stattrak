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
    }
  }
  
  // <------------------- Images ------------------->

  // Common
  val valorantThumbnail = "https://c.tenor.com/wuYSt-pgcoEAAAAM/valorant-games.gif"
  val defaultMapImg = "https://images2.alphacoders.com/132/1322753.jpeg"

  // Maps
  val bindImg = "https://static.wikia.nocookie.net/valorant/images/2/23/Loading_Screen_Bind.png"
  val splitImg = "https://static.wikia.nocookie.net/valorant/images/d/d6/Loading_Screen_Split.png"
  val iceboxImg = "https://static.wikia.nocookie.net/valorant/images/1/13/Loading_Screen_Icebox.png"
  val breezeImg = "https://static.wikia.nocookie.net/valorant/images/1/10/Loading_Screen_Breeze.png"
  val fractureImg = "https://static.wikia.nocookie.net/valorant/images/f/fc/Loading_Screen_Fracture.png"
  val pearlImg = "https://static.wikia.nocookie.net/valorant/images/a/af/Loading_Screen_Pearl.png"
  val havenImg = "https://static.wikia.nocookie.net/valorant/images/7/70/Loading_Screen_Haven.png"
  val lotusImg = "https://static.wikia.nocookie.net/valorant/images/d/d0/Loading_Screen_Lotus.png"
  val ascentImg = "https://static.wikia.nocookie.net/valorant/images/e/e7/Loading_Screen_Ascent.png"
  val sunsetImg = "https://static.wikia.nocookie.net/valorant/images/5/5c/Loading_Screen_Suns"
  // Agents
  val brimstone = "https://static.wikia.nocookie.net/valorant/images/4/4d/Brimstone_icon.png"
  val viper = "https://static.wikia.nocookie.net/valorant/images/5/5f/Viper_icon.png"
  val omen = "https://static.wikia.nocookie.net/valorant/images/b/b0/Omen_icon.png"
  val cypher = "https://static.wikia.nocookie.net/valorant/images/8/88/Cypher_icon.png"
  val sova = "https://static.wikia.nocookie.net/valorant/images/4/49/Sova_icon.png"
  val sage = "https://static.wikia.nocookie.net/valorant/images/7/74/Sage_icon.png"
  val phoenix = "https://static.wikia.nocookie.net/valorant/images/1/14/Phoenix_icon.png"
  val jett = "https://static.wikia.nocookie.net/valorant/images/3/35/Jett_icon.png"
  val raze = "https://static.wikia.nocookie.net/valorant/images/9/9c/Raze_icon.png"
  val breach = "https://static.wikia.nocookie.net/valorant/images/5/53/Breach_icon.png"
  val reyna = "https://static.wikia.nocookie.net/valorant/images/b/b0/Reyna_icon.png"
  val killjoy = "https://static.wikia.nocookie.net/valorant/images/1/15/Killjoy_icon.png"
  val skye = "https://static.wikia.nocookie.net/valorant/images/3/33/Skye_icon.png"
  val yoru = "https://static.wikia.nocookie.net/valorant/images/d/d4/Yoru_icon.png"
  val astra = "https://static.wikia.nocookie.net/valorant/images/0/08/Astra_icon.png"
  val kayo = "https://static.wikia.nocookie.net/valorant/images/f/f0/KAYO_icon.png"
  val chamber = "https://static.wikia.nocookie.net/valorant/images/0/09/Chamber_icon.png"
  val neon = "https://static.wikia.nocookie.net/valorant/images/d/d0/Neon_icon.png"
  val fade = "https://static.wikia.nocookie.net/valorant/images/a/a6/Fade_icon.png"
  val harbor = "https://static.wikia.nocookie.net/valorant/images/f/f3/Harbor_icon.png"
  val gekko = "https://static.wikia.nocookie.net/valorant/images/6/66/Gekko_icon.png"
  val deadlock = "https://static.wikia.nocookie.net/valorant/images/e/eb/Deadlock_icon.png"
  val iso = "https://static.wikia.nocookie.net/valorant/images/b/b7/Iso_icon.png"
}
