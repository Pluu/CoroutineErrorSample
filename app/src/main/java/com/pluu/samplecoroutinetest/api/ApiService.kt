package com.pluu.samplecoroutinetest.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {
    @GET("/repos/{owner}/{repo}/contributors")
    suspend fun contributors(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ): List<Contributor>
}

data class Contributor(
    val login: String,
    val contributions: Int
)

object GitHubApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}