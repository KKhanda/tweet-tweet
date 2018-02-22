package com.scalacourse.app

import org.json4s.{DefaultFormats, Formats}
import org.scalatra._
import org.scalatra.json.JacksonJsonSupport

class RestServlet extends ScalatraServlet with JacksonJsonSupport {

  protected implicit val jsonFormats: Formats = DefaultFormats

  get("/") {
    views.html.hello()
  }

}
