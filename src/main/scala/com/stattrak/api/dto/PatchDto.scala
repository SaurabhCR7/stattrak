package com.stattrak
package api.dto

import java.time.LocalDateTime

case class PatchDto(banner_url: String, date: LocalDateTime, title: String, url: String, category: String)

case class PatchResponse(status: Int, data: Array[PatchData])

case class PatchData(banner_url: String, category: String, date: String, external_link: Option[String],
                     title: String, url: String)
