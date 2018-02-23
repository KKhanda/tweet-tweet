package com.scalacourse.dao

import com.scalacourse.models.User

import scala.collection.mutable

object UserDao {

  var usersMap: mutable.TreeMap[String, User] = mutable.TreeMap[String, User]()
  var idCounter: Int = 0

  def addUser(email: String, nickname: String, password: String): User = {
    idCounter += 1
    val newUser = User(idCounter, email, nickname, password)
    usersMap.put(email, newUser)
    newUser
  }

  def findUser(email: String): Option[User] = {
    if (usersMap.contains(email)) {
      usersMap.get(email)
    } else {
      None
    }
  }
}
