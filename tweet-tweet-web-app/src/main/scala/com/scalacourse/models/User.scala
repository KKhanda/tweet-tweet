package com.scalacourse.models

case class User(id: Long, email: String, nickname: String, password: String, tweets: List[Twit]) {

  override def hashCode: Int = id.hashCode()
}
