package com.scalacourse.dao

import com.scalacourse.models.{User}
import org.mindrot.jbcrypt.BCrypt

import scala.collection.mutable

object UserDao {

  var byEmailMap: mutable.TreeMap[String, User] = mutable.TreeMap[String, User]()
  var byIdMap: mutable.TreeMap[Long, User] = mutable.TreeMap[Long, User]()
  var idCounter: Long = 0  // Sequence for id generation

  def addUser(email: String, nickname: String, password: String): User = {
    idCounter += 1
    val newUser = User(idCounter, email, nickname, BCrypt.hashpw(password, BCrypt.gensalt(12)))
    byEmailMap.put(email, newUser)
    byIdMap.put(idCounter, newUser)
    newUser
  }

  def getUser(email: String): Option[User] = {
    byEmailMap.get(email)
  }

  def getUser(id: Long): Option[User] = {
    byIdMap.get(id)
  }
}
