package com.example.blog

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BlogConfiguration {

  @Bean
  fun dbInit(userRepo: UserRepository, articleRepo: ArticleRepository) = ApplicationRunner {
    val user1 = userRepo.save(User(
      "rty1", "peter", "Lam", "hello, i am peter"
    ))
    articleRepo.save(Article(
      "article1", "big news!", "someone's falling in the sea", user1
    ))

  }

}








