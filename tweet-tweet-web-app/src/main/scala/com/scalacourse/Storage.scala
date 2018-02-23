package main.scala.com.scalacourse

import com.scalacourse.models.User

import scala.collection.mutable

object Storage {
  val users = mutable.HashMap.empty[Long, User]
}
