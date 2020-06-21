package com.example.blog.domain.second

import javax.persistence.*

@Entity
class Student (
  @Id @GeneratedValue var id: Long,
  var name: String,
  var student_no: Int
)


