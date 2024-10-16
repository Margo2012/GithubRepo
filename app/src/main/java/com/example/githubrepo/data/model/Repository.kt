package com.example.githubrepo.data.model

data class Repository(
    val id: Long,
    val name: String,
    val full_name: String,
    val owner: Owner,
    val description: String?
)
