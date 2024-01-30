package com.stattrak
package models

case class User(username: String, tag: String) {
  override def toString: String = {
    username + "#" + tag
  }
}
