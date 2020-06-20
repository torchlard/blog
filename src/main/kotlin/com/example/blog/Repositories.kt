package com.example.blog

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import javax.transaction.Transactional


interface ArticleRepository : CrudRepository<Article,Long> {
  fun findBySlug(slug: String): Article?
  fun findAllByOrderByAddedAtDesc(): Iterable<Article>

  @Transactional @Modifying
  fun deleteArticleBySlug(slug: String)

  @Transactional @Modifying
  fun deleteArticlesByHeadline(headline: String)

  @Query("select * from Article t limit ?1", nativeQuery = true)
  fun get1(num: Int): Iterable<Article>

  fun findByContentContaining(content: String): List<SubArticle>
//  fun findByContentContaining2(content: String): List<Article>



}

interface UserRepository: CrudRepository<User, Long> {
  fun findByLogin(login: String): User?
  fun findAllByOrderByFirstname(): Iterable<User>

  @Transactional @Modifying
  fun deleteUserByLogin(login: String)
}









