package com.example.postsapplication.users.data

import com.example.postsapplication.users.data.model.UserResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface UsersApi {
    @GET("users")
    suspend fun getUsers(): List<UserResponse>
}