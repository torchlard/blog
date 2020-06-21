package com.example.blog.repository.primary

import com.example.blog.domain.primary.Article
import com.example.blog.domain.primary.User
import com.example.blog.service.ArticleUser
import com.example.blog.service.SubArticle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import javax.transaction.Transactional

//@Repository
interface ArticleRepository : JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
  fun findBySlug(slug: String): Article?
  fun findAllByOrderByAddedAtDesc(): Iterable<Article>

  @Transactional
  @Modifying
  fun deleteArticleBySlug(slug: String)

  @Transactional
  @Modifying
  fun deleteArticlesByHeadline(headline: String)

  @Query("select * from Article t limit ?1", nativeQuery = true)
  fun get1(num: Int): Iterable<Article>

  fun findByContentContaining(content: String): List<SubArticle>

  @Query("select a.content, u.firstname " +
    " from Article a inner join User u on a.author_id=u.id", nativeQuery = true)
  fun join1(id: Long): List<ArticleUser>

}

//@Repository
interface UserRepository : JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
  fun findByLogin(login: String): User?
  fun findAllByOrderByFirstname(): Iterable<User>

  @Transactional
  @Modifying
  fun deleteUserByLogin(login: String)
}


