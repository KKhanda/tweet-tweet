package com.scalacourse.dao

import com.scalacourse.exceptions.{BadCredentialsException, EmailExistsException}
import com.scalacourse.models.User
import org.mindrot.jbcrypt.BCrypt

import scala.collection.mutable

object UserDao {

  var byEmailMap: mutable.TreeMap[String, User] = mutable.TreeMap[String, User]()
  var byIdMap: mutable.TreeMap[Int, User] = mutable.TreeMap[Int, User]()
  var idCounter: Int = 0  // Sequence for id generation
  var subscribtions: List[User]

  def addUser(email: String, nickname: String, password: String): Unit = {
    idCounter += 1
    if (!byEmailMap.contains(email)) {
      val newUser = User(idCounter, email, nickname, BCrypt.hashpw(password, BCrypt.gensalt(12)))
      byEmailMap.put(email, newUser)
      byIdMap.put(idCounter, newUser)
    } else {
      throw new EmailExistsException("Email " + email + " already exists.")
    }
  }

  def checkPassword(email: String, password: String): Boolean = {
    if (byEmailMap.contains(email)) {
      BCrypt.checkpw(password, byEmailMap(email).password)
    } else {
      throw new BadCredentialsException("Bad credentials")
    }
  }

  def getUser(email: String): Option[User] = {
    byEmailMap.get(email)
  }

  def getUser(id: Int): Option[User] = {
    byIdMap.get(id)
  }

  def addSubscribe(user: User) = {
    user :: subscribtions
  }
}
