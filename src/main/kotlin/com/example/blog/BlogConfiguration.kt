package com.example.blog

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

  @Bean
  fun dbInit(userRepo: UserRepository, articleRepo: ArticleRepository) = ApplicationRunner {
    // articleRepo.deleteAll()
    // userRepo.deleteAll()
    // val user1 = userRepo.save(User("rty1", "peter", "Lam", "hello, i am peter"))
    // val user2 = userRepo.save(User("w1", "mary", "Tam", "hello, i am mary"))
    // val user3 = userRepo.save(User("w2", "peter", "Lai", "hello, i am peter 2nd"))
    // articleRepo.save(Article("article1", "big news!", "someone's falling in the sea", user1))
    // articleRepo.save(Article("article2", "title2", "earn big money", user1))
    // articleRepo.save(Article("article3", "title3", "get security", user2))
    // articleRepo.save(Article("article4", "breaking news!", "someone's flying", user3))



  }

}








