package com.stattrak.updaters

import com.stattrak.api.ValorantApi
import com.stattrak.api.dto.{PatchDto, PatchResponse}
import com.stattrak.clients.DiscordClient
import com.stattrak.store.UserStore
import com.stattrak.utils.{DiscordMsgGenerator, Logging}

import java.nio.file.{Files, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.collection.mutable
import scala.io.Source

class PatchUpdater extends Updater with Logging {
  private val patchStoreFilepath = "patchNotes.txt"
  private var currentPatchTitle = readPatchTitleFromDisk
  private val path = Paths.get(patchStoreFilepath)

  def checkForUpdate(): Unit = {
    val channels = new mutable.HashSet[Long]()
    UserStore.cache.forEach((user, userdata) => channels.add(userdata.channelId))
    val recentPatchData = getRecentPatch
    if (currentPatchTitle != recentPatchData.title) {
      channels.foreach(channel => notifyChannel(channel, recentPatchData))
      updatePatch(recentPatchData.title)
    }
  }

  private def getRecentPatch: PatchDto = {
    val response = ValorantApi.fetchPatchData
    try {
      toPatchDTO(response)
    } catch {
      case ex: Exception =>
        error(s"Error while parsing patchDTO with response $response", ex)
        throw ex
    }
  }

  private def toPatchDTO(response: PatchResponse): PatchDto = {
    val data = response.data(0)
    val bannerUrl = data.banner_url
    val date = toLocalDateTime(data.date)
    val title = data.title
    val url = data.url
    val category = data.category
    PatchDto(bannerUrl, date, title, url, category)
  }

  private def notifyChannel(channel: Long, patchDto: PatchDto): Unit = {
    val msg = DiscordMsgGenerator.getPatchMsg(channel, patchDto)
    DiscordClient.sendMessage(msg)
  }
  private def updatePatch(title: String): Unit = {
    currentPatchTitle = title
    writeNewPatchToDisk(title)
  }

  private def readPatchTitleFromDisk: String = {
    try {
      val source = Source.fromFile(patchStoreFilepath)
      val str = source.mkString
      source.close()
      str
    } catch {
      case ex: Exception =>
        error("Unable to read patch store file", ex)
        ""
    }
  }

  private def writeNewPatchToDisk(newPatchTitle: String): Unit = {
    try {
      Files.writeString(Paths.get(patchStoreFilepath), newPatchTitle)
    } catch {
      case ex: Exception => error("Unable to read patch store file", ex)
    }
  }

  private def toLocalDateTime(date: String): LocalDateTime = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime.parse(date, formatter)
  }
}
