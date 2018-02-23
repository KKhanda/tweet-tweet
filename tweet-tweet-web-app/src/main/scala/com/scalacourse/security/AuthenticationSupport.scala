package com.scalacourse.security

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.scalacourse.models.User
import org.scalatra.ScalatraBase
import org.scalatra.auth.strategy.BasicAuthSupport
import org.scalatra.auth.{ScentryConfig, ScentrySupport}


trait AuthenticationSupport extends ScentrySupport[User] with BasicAuthSupport[User] {
  self: ScalatraBase =>

  protected def fromSession: PartialFunction[String, User] = { case id: String => User(id) }
  protected def toSession: PartialFunction[User, String] = { case user: User => user.id }

  var realm = "Bearer Authentication"
  protected val scentryConfig = new ScentryConfig {}.asInstanceOf[ScentryConfiguration]

  override protected def configureScentry: Unit = {
    scentry.unauthenticated {
      scentry.strategies("Bearer").unauthenticated()
    }
  }

  override protected def registerAuthStrategies: Unit = {
    scentry.register("Bearer", app => new BearerAuthentication(app, realm))
  }

  // verifies if the request is a Bearer request
  protected def auth()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = {
    val baReq = new BearerAuthRequest(request)
    if(!baReq.providesAuth) {
      halt(401, "Unauthenticated")
    }
    if(!baReq.isBearerAuth) {
      halt(400, "Bad Request")
    }
    scentry.authenticate("Bearer")
  }
}
