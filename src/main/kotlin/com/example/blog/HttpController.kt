package com.example.blog

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/api/article")
class ArticleController(private val repo: ArticleRepository){

  @GetMapping("/")
  fun findAll() = repo.findAllByOrderByAddedAtDesc()

  @GetMapping("/{slug}")
  fun findOne(@PathVariable slug: String) =
    repo.findBySlug(slug) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this article does not exist")
}


@RestController
@RequestMapping("/api/user")
class UserController(private val repo: UserRepository){

  @GetMapping("/")
  fun findAll() = repo.findAll()


  @GetMapping("/{login}")
  fun findOne(login: String) = repo.findByLogin(login) ?:
    throw ResponseStatusException(HttpStatus.NOT_FOUND, "user not exist")


}

















