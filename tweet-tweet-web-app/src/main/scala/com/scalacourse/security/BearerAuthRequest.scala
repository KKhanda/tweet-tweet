package com.scalacourse.security

import java.util.Locale

import javax.servlet.http.HttpServletRequest

class BearerAuthRequest(r: HttpServletRequest) {

  private val AUTHORIZATION_KEYS = List("Authorization", "HTTP_AUTHORIZATION", "X-HTTP_AUTHORIZATION", "X_HTTP_AUTHORIZATION")
  def parts: List[String] = authorizationKey map { r.getHeader(_).split(" ", 2).toList } getOrElse Nil
  def scheme: Option[String] = parts.headOption.map(sch => sch.toLowerCase(Locale.ENGLISH))
  def token : String = parts.lastOption getOrElse ""

  private def authorizationKey = AUTHORIZATION_KEYS.find(r.getHeader(_) != null)

  def isBearerAuth: Boolean = (false /: scheme) { (_, sch) => sch == "bearer" }
  def providesAuth: Boolean = authorizationKey.isDefined

}
