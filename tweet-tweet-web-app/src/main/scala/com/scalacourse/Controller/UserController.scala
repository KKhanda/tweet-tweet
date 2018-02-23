package com.scalacourse.Controller

import org.json4s.{DefaultFormats, Formats}
import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport

import scala.collection.mutable

case class User(id: Long, email: String, nickname: String, password: String)

class UserController extends ScalatraServlet with JacksonJsonSupport {

  override protected implicit def jsonFormats: Formats = DefaultFormats

  val user = mutable.HashMap.empty[String, User]


  before() {
    contentType = formats("json")
  }

  post("/user/:id/subscribe") {

  }

  get("/user/:id/followers") {

  }

  get("/user/:id/followings") {

  }

}
