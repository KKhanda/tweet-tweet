package com.scalacourse.app

import org.scalatra._

class TweetTweetServlet extends ScalatraServlet {

  get("/") {
    views.html.hello()
  }

}
