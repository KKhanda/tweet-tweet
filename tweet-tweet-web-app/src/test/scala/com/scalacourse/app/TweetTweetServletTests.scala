package com.scalacourse.app

import org.scalatra.test.scalatest._

class TweetTweetServletTests extends ScalatraFunSuite {

  addServlet(classOf[TweetTweetServlet], "/*")

  test("GET / on TweetTweetServlet should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
