package com.example.postsapplication.users.data.repository

import com.example.postsapplication.network.NetworkClient
import com.example.postsapplication.posts.domain.model.UserModel
import com.example.postsapplication.posts.domain.repository.DataRepository
import com.example.postsapplication.users.data.UsersApi
import com.example.postsapplication.users.data.mapper.UsersMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepositoryImpl(private val usersMapper: UsersMapper = UsersMapper()) :
    DataRepository<List<UserModel>> {
    override suspend fun getData(): Flow<List<UserModel>> {
        val users = NetworkClient.createService(UsersApi::class.java).getUsers().map {
            usersMapper.mapFrom(it)
        }
        return flow { emit(users) }
    }
}