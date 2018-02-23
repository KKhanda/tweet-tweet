package com.scalacourse.app

import com.scalacourse.dao.{TokenStore, UserDao}
import com.scalacourse.dto.{TokenDto, UserCredentialsDto, UserDto}
import com.scalacourse.exceptions.{BadCredentialsException, EmailExistsException}
import com.scalacourse.models.User
import com.scalacourse.security.AuthenticationSupport
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

class RestServlet extends ScalatraServlet with JacksonJsonSupport with AuthenticationSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  val subscribe = List(auth.get)

  before() {
    contentType = formats("json")
  }

  get("/") {
    views.html.hello()
  }

  post("/register") {
    val userData = parsedBody.extract[UserDto]
    try {
      UserDao.addUser(userData.email, userData.nickname, userData.password)
    } catch {
      case x: EmailExistsException => response.sendError(406, x.message)
    }
  }

  post("/signin") {
    val userData = parsedBody.extract[UserCredentialsDto]
    try {
      if (UserDao.checkPassword(userData.email, userData.password)) {
        val user: User = UserDao.getUser(userData.email).get
        val token = TokenStore.addToken(user)
        Map (
          "token" -> token.token
        )
      }
    } catch {
      case x: BadCredentialsException => response.sendError(401, x.message)
    }
  }

  post("/signout") {
    val token = parsedBody.extract[TokenDto]
    TokenStore.removeToken(token.token)
  }

  post("/user/subscribe/:id/?") {
    val sub_user_id = params("id")
    subscribe :+ UserDao.getUser(sub_user_id)
    User
  }

  get("/user/followers") {

  }

  get("/user/subscriptions") {
    subscribe.tail
  }

}
