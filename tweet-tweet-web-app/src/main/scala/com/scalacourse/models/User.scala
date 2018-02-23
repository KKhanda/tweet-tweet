package com.scalacourse.models

// Maybe we should add list of twits here?
case class User(id: Long, email: String, nickname: String, password: String, tweets: List[Twit])
