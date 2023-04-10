package com.example.postsapplication.posts.domain.usecase

import com.example.postsapplication.posts.domain.model.PostInfoModel
import com.example.postsapplication.posts.domain.model.PostModel
import com.example.postsapplication.posts.domain.model.UserModel
import com.example.postsapplication.posts.domain.repository.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip

class GetPostsUseCase(
    private val postsRepository: DataRepository<List<PostModel>>,
    private val usersRepository: DataRepository<List<UserModel>>
) {
    suspend fun execute(): Flow<List<PostInfoModel>> {
        return postsRepository.getData().zip(usersRepository.getData()) { posts, users ->
            posts.map { post ->
                val user = users.find {
                    it.id == post.userId
                }
                PostInfoModel(post.id, user?.name, post.title, post.content)
            }
        }.flowOn(Dispatchers.IO)

    }
}