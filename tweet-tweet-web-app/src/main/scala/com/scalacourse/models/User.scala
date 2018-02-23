package com.scalacourse.models

import scala.collection.mutable

case class User(id: Long, email: String, nickname: String, password: String,
                tweets: mutable.MutableList[Twit] = mutable.MutableList.empty) {

  override def hashCode: Int = id.hashCode()
}
