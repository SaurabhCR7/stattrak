package com.stattrak
package utils

import com.typesafe.scalalogging.Logger

trait Logging {
  private val logger = Logger(getClass.getName)

  def info(msg: String): Unit = logger.info(msg)

  def error(msg: String, exception: Exception): Unit = logger.error(msg, exception)
  
  def error(msg: String): Unit = logger.error(msg)
  
  def warn(msg: String): Unit = logger.warn(msg)

  def debug(msg: String): Unit = logger.debug(msg)
}
