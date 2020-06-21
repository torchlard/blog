package com.example.blog.service

import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime

interface SubArticle {
    var addedAt: LocalDateTime
    var content: String
    var id: Long

    @Value("#{target.content+' '+target.headline}")
    fun getNewName(): String
}

interface ArticleUser {
    var content: String
    var firstname: String
}

interface Article2 {
    var id: Long
    var content: String
}



