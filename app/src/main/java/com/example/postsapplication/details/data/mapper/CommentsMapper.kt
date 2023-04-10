package com.example.postsapplication.details.data.mapper

import com.example.postsapplication.details.data.model.CommentResponse
import com.example.postsapplication.details.domain.model.CommentModel

class CommentsMapper {
    fun mapFrom(commentResponse: CommentResponse): CommentModel {
        return commentResponse.run {
            CommentModel(postId, name, body)
        }
    }
}