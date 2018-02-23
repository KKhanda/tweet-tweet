package com.scalacourse.security

import com.scalacourse.dao.{TokenStore, UserDao}
import com.scalacourse.models.User
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.mindrot.jbcrypt.BCrypt
import org.scalatra.{ScalatraBase, Unauthorized}
import org.scalatra.auth.ScentryStrategy

class BearerAuthentication (protected override val app: ScalatraBase, realm: String) extends ScentryStrategy[User] {

  implicit def request2BearerAuthRequest(r: HttpServletRequest): BearerAuthRequest = new BearerAuthRequest(r)

  protected def validate(email: String, password: String): Option[User] = {
    val user: Option[User] = UserDao.byEmailMap.get(email)
    if (user.isDefined) {
      if (BCrypt.checkpw(password, user.get.password)) user else None
    } else {
      None
    }
  }

  protected def validate(token: String): Option[User] = {
    TokenStore.findUser(token)
  }

  override def isValid(implicit request: HttpServletRequest): Boolean = request.isBearerAuth && request.providesAuth

  override def unauthenticated()(implicit request: HttpServletRequest, response: HttpServletResponse) {
    app halt Unauthorized()
  }

  def authenticate()(implicit request: HttpServletRequest, response: HttpServletResponse): Option[User] = validate(request.token)
}