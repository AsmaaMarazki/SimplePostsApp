package com.example.postsapplication.details.data.repository

import com.example.postsapplication.details.data.CommentsApi
import com.example.postsapplication.details.data.mapper.CommentsMapper
import com.example.postsapplication.details.domain.model.CommentModel
import com.example.postsapplication.details.domain.repository.CommentsRepository
import com.example.postsapplication.network.NetworkClient

class CommentsRepositoryImpl(private val mapper: CommentsMapper=CommentsMapper()) :
    CommentsRepository {
    override suspend fun getComments(): List<CommentModel> {
        return NetworkClient.createService(CommentsApi::class.java).getComments().map {
            mapper.mapFrom(it)
        }
    }
}