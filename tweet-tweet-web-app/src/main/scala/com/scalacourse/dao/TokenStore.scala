package com.scalacourse.dao

import authentikat.jwt.{JsonWebToken, JwtClaimsSet, JwtHeader}
import com.scalacourse.dto.TokenDto
import com.scalacourse.models.User

import scala.collection.mutable

object TokenStore {

  val tokenMap: mutable.TreeMap[String, User] = mutable.TreeMap[String, User]()
  val header = JwtHeader("HS256")  // TODO: take from property file


  def addToken(user: User): TokenDto = {
    val jwtClaimsSet = JwtClaimsSet(Map(user.email -> user.nickname))
    val jwtToken = JsonWebToken(header, jwtClaimsSet, "secretKey")  // TODO: take secret from properties
    tokenMap.put(jwtToken, user)
    new TokenDto(jwtToken)
  }

  def removeToken(token: String): Unit = {
    tokenMap.remove(token)
  }

  def findUser(token: String): Option[User] = {
    if( JsonWebToken.validate(token, "secretKey")) tokenMap.get(token) else None
  }
}
