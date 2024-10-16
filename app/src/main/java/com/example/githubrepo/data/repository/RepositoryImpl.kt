package com.example.githubrepo.data.repository

import com.example.githubrepo.data.model.Issue
import com.example.githubrepo.data.model.Repository
import com.example.githubrepo.network.GitHubApi

class RepositoryImpl(private val api: GitHubApi) {

    suspend fun searchRepositories(query: String, page: Int): List<Repository>? {
        val response = api.searchRepositories(query, page)
        return if (response.isSuccessful) {
            response.body()?.items
        } else {
            null
        }
    }

    suspend fun getIssues(owner: String, repo: String): List<Issue>? {
        val response = api.getIssues(owner, repo)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}