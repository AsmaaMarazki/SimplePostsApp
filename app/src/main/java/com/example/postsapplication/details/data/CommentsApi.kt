package com.example.postsapplication.details.data

import com.example.postsapplication.details.data.model.CommentResponse
import retrofit2.http.GET

interface CommentsApi {
    @GET("comments")
    suspend fun getComments():List<CommentResponse>
}