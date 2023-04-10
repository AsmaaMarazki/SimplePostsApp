package com.example.postsapplication.posts.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataRepository<T> {
    suspend fun getData(): Flow<T>
}