package com.example.postsapplication.details.domain.repository

import com.example.postsapplication.details.domain.model.CommentModel
import kotlinx.coroutines.flow.Flow

interface CommentsRepository {
    suspend fun getComments(): List<CommentModel>
}