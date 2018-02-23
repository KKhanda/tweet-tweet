package com.scalacourse.dao

import com.scalacourse.models.{Twit, User}

object TwitDao {
  var idCounter = 0
  def createTwit(text: String, author: User): Twit = {
    idCounter += 1
    Twit(idCounter, text, author, System.currentTimeMillis())
  }
}
