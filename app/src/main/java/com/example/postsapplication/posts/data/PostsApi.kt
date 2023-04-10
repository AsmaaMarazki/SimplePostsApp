package com.example.postsapplication.posts.data

import com.example.postsapplication.posts.data.model.PostResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface PostsApi {
    @GET("posts")
    suspend fun getPosts(): List<PostResponse>
}