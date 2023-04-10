package com.example.postsapplication.posts.data.repository

import com.example.postsapplication.network.NetworkClient
import com.example.postsapplication.posts.data.PostsApi
import com.example.postsapplication.posts.data.mapper.PostsMapper
import com.example.postsapplication.posts.domain.model.PostModel
import com.example.postsapplication.posts.domain.repository.DataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PostsRepositoryImpl(private val postsMapper: PostsMapper = PostsMapper()) :
    DataRepository<List<PostModel>> {
    override suspend fun getData(): Flow<List<PostModel>> {
        val posts = NetworkClient.createService(PostsApi::class.java).getPosts().map {
            postsMapper.mapFrom(it)
        }
        return flow { emit(posts) }
    }
}