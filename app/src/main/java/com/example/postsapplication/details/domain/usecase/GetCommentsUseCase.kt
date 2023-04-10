package com.example.postsapplication.details.domain.usecase

import com.example.postsapplication.details.domain.model.CommentModel
import com.example.postsapplication.details.domain.repository.CommentsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class GetCommentsUseCase(private val commentsRepository: CommentsRepository) {

   suspend fun execute(postId:Int):List<CommentModel>{
       return withContext(Dispatchers.IO) {
            commentsRepository.getComments().filter {
                it.postId == postId
            }
        }
    }
}