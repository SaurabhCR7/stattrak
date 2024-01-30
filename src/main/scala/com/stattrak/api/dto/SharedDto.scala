package com.stattrak
package api.dto

case class Character(id: String, name: String)

case class Damage(made: Int, received: Int)

case class ImageUrls(small: String, large: String, triangle_down: String, triangle_up: String)

case class Map(name: String, id: String)

case class Season(id: String, _short: String)

case class Shots(head: Int, body: Int, leg: Int)

case class Teams(red: Int, blue: Int)