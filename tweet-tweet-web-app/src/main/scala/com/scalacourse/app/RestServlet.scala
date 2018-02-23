package com.scalacourse.app

import com.scalacourse.security.{AuthenticationSupport}
import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

class RestServlet extends ScalatraServlet
  with JacksonJsonSupport
  with AuthenticationSupport
  with CorsSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/") {
    views.html.hello()
  }

  post("/register") {
    
  }

  post("/sign-in") {

  }

  post("/sign-out") {

  }
}
