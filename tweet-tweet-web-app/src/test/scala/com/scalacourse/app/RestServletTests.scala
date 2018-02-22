package com.scalacourse.app

import org.scalatra.test.scalatest._

class RestServletTests extends ScalatraFunSuite {

  addServlet(classOf[RestServlet], "/*")

  test("GET / on TweetTweetServlet should return status 200"){
    get("/"){
      status should equal (200)
    }
  }

}
