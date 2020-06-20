package com.example.blog

import com.fasterxml.jackson.annotation.JsonCreator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional


@RestController
@RequestMapping("/api/article")
class ArticleController(private val repo: ArticleRepository, private val gen: DataGenerator){

  @GetMapping("/")
  fun findAll() = repo.findAllByOrderByAddedAtDesc()

  @GetMapping("/{slug}")
  fun findOne(@PathVariable slug: String) =
    repo.findBySlug(slug) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "this article does not exist")

  @DeleteMapping("/{slug}")
  fun deleteArticle(@PathVariable slug: String) = repo.deleteArticleBySlug(slug)

  @PostMapping("/generate")
  @ResponseStatus(value = HttpStatus.OK)
  fun generateAlls() {
    gen.generateArticles()
  }

  @DeleteMapping("/headline/{headline}")
  fun deleteHeadline(@PathVariable headline: String) = repo.deleteArticlesByHeadline(headline)

  @GetMapping("/get1/{num}")
  fun get1(@PathVariable num: Int) = repo.get1(num)

  @GetMapping("/get2/{keyword}")
  fun get2(@PathVariable keyword: String)
          = repo.findByContentContaining(keyword).filter{ it.id!! < 50}

}


@RestController
@RequestMapping("/api/user")
class UserController(private val repo: UserRepository){

  @GetMapping("/")
  fun findAll() = repo.findAll()

  @GetMapping("/orders")
  fun orders() = repo.findAllByOrderByFirstname()

  @GetMapping("/{login}")
  fun findOne(@PathVariable login: String) = repo.findByLogin(login) ?:
    throw ResponseStatusException(HttpStatus.NOT_FOUND, "user not exist")

  data class UserDTO @JsonCreator constructor(val description: String, val firstname: String, val lastname: String, val login: String)

  @PostMapping(path=["/add"])
  @ResponseStatus(value = HttpStatus.OK)
  fun addUser(@RequestBody userdto: UserDTO) =
    (1..5).forEach{ repo.save(User(userdto.description, userdto.firstname, userdto.lastname, userdto.login)) }
//    return ResponseEntity.ok(HttpStatus.OK)


  @DeleteMapping("/{login}")
  fun deleteMany(@PathVariable login: String) = repo.deleteUserByLogin(login)


}

















