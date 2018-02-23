package com.scalacourse.dao

import com.scalacourse.models.User

import scala.collection.immutable.TreeMap

object TokenStore {

  val tokenMap: TreeMap[String, User] = TreeMap[String, User]()

  def findUser(token: String): Option[User] = {
    tokenMap.get(token)
  }
}
