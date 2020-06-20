package com.example.blog

import org.springframework.stereotype.Service

@Service
class DataGenerator(private val articleRepo: ArticleRepository, private val userRepo: UserRepository) {

    fun generateArticles(){
        val users: List<User> = userRepo.findAll().take(5).toList()
        val names = listOf("aaa","bbb","ccc","ddd")
        val articles = (1..10).map{
            Article(content=names.random(), headline=names.random(), title=names.random(), author=users.random())
        }
        articleRepo.saveAll(articles)
    }



}

