package com.example.blog

import org.springframework.beans.factory.annotation.Value
import java.time.LocalDateTime

interface SubArticle {
    var addedAt: LocalDateTime
    var content: String
    var id: Long?

    @Value("#{target.content + target.id}")
    var newName: String
}


