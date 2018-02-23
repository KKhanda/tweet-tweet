import com.scalacourse.dao.{TwitDao, UserDao}
import com.scalacourse.models.User
import com.scalacourse.security.AuthenticationSupport
import org.json4s.JsonAST._
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

import scala.util.{Failure, Success, Try}

case class TweetInfo(text: String)

class TweetServlet extends ScalatraServlet
  with JacksonJsonSupport
  with AuthenticationSupport
  with CorsSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

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
            user.tweets = user.tweets.map(tweet => if (tweet.id == id) tweet.copy(text = text.values) else tweet)
          case _ => UnprocessableEntity()
        }
      case Failure(_) => NotFound("")
    }

  }
}
