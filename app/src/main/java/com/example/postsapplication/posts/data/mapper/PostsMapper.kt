package com.example.postsapplication.posts.data.mapper

import com.example.postsapplication.posts.data.model.PostResponse
import com.example.postsapplication.posts.domain.model.PostModel

class PostsMapper {
    fun mapFrom(postResponse: PostResponse): PostModel {
        return postResponse.run {
            PostModel(id, userId, title, content)
        }
    }
}