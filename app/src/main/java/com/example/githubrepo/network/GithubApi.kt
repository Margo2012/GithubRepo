package com.example.githubrepo.network

import com.example.githubrepo.data.model.Issue
import com.example.githubrepo.data.model.Repository
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
        @Query("page") page: Int
    ): Response<SearchResponse>

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Query("per_page") perPage: Int = 30
    ): Response<List<Issue>>
}

data class SearchResponse(
    val items: List<Repository>
)