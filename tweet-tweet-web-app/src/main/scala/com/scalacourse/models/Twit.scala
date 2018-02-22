package com.scalacourse.models

class Twit(val id: Long, val text: String, val author: User,
           val submissionTime: Long, val likes: Int, val dislikes: Int)
