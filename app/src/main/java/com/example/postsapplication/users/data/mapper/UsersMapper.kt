package com.example.postsapplication.users.data.mapper

import com.example.postsapplication.posts.domain.model.UserModel
import com.example.postsapplication.users.data.model.UserResponse

class UsersMapper {
    fun mapFrom(userResponse: UserResponse): UserModel {
        return userResponse.run {
            UserModel(id, name)
        }
    }
}