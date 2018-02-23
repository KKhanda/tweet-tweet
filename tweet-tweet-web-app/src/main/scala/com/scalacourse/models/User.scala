package com.scalacourse.models

case class User(id: Long, email: String, nickname: String, password: String, tweets: List[Twit] = Nil) {

  override def hashCode: Int = id.hashCode()
}
