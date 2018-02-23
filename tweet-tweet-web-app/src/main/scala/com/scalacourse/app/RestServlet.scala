package com.scalacourse.app

import com.scalacourse.dao.{TokenStore, TwitDao, UserDao}
import com.scalacourse.dto.{TokenDto, UserCredentialsDto, UserDto}
import com.scalacourse.exceptions.{BadCredentialsException, EmailExistsException}
import com.scalacourse.models.User
import com.scalacourse.security.AuthenticationSupport
import org.json4s.JsonAST.{JLong, JString}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

import scala.util.{Failure, Success, Try}

class RestServlet extends ScalatraServlet with JacksonJsonSupport with AuthenticationSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

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

  post("/subscribe/:id") {
    val user: User = auth.get
    Try(params("id").toInt) match {
      case Success(id) =>
        UserDao.getUser(id).fold(NotFound("")){ toFollow =>
          user.following = toFollow :: user.following
          Ok()
        }
      case Failure(_) => NotFound("")
    }
  }

  get("/feed/") {
    auth.get.following.flatMap(_.tweets)
  }

  get("/feed/:id") {
    Try(params("id").toInt) match {
      case Success(id) =>
        UserDao.getUser(id).fold(NotFound(""))(user => Ok(user.following.flatMap(_.tweets)))
      case Failure(_) => NotFound("")
    }
  }

  post("/tweet/") {
    val user: User = auth.get
    parsedBody \ "text" match {
      case text: JString =>
        val newTweet = TwitDao.createTwit(text.values, user)
        user.tweets = newTweet :: user.tweets
        Created(newTweet)
      case _ => UnprocessableEntity()
    }
  }

  delete("/tweet/:id") {
    val user: User = auth.get
    Try(params("id").toInt) match {
      case Success(id) =>
        user.tweets = user.tweets.filterNot(_.id == id)
        Ok()
      case Failure(_) => NotFound("")
    }
  }

  put("/tweet/:id") {
    val user: User = auth.get
    Try(params("id").toInt) match {
      case Success(id) =>
        parsedBody \ "text" match {
          case text: JString =>
            user.tweets.find(_.id == id).foreach(_.text = text.values)
          case _ => UnprocessableEntity()
        }
      case Failure(_) => NotFound("")
    }
  }

  post("/retweet/:id") {
    val user: User = auth.get
    Try(params("id").toInt) match {
      case Success(id) =>
        parsedBody \ "author" match {
          case author: JLong =>
            UserDao.getUser(author.values).fold(NotFound("")){ author =>
              author.tweets.find(_.id == id).fold(NotFound("")){ tweet =>
                author.tweets = tweet :: author.tweets
                Ok()
              }
            }
          case _ => UnprocessableEntity()
        }
      case Failure(_) => NotFound("")
    }
  }
}
