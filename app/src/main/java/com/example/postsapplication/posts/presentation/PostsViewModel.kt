package com.example.postsapplication.posts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsapplication.posts.data.repository.PostsRepositoryImpl
import com.example.postsapplication.posts.domain.model.PostInfoModel
import com.example.postsapplication.posts.domain.usecase.GetPostsUseCase
import com.example.postsapplication.users.data.repository.UsersRepositoryImpl
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase(
        postsRepository = PostsRepositoryImpl(),
        usersRepository = UsersRepositoryImpl()
    )
) : ViewModel() {

    private val _postsSharedFlow = MutableSharedFlow<List<PostInfoModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val postsFlow: Flow<List<PostInfoModel>> = _postsSharedFlow.distinctUntilChanged()

    private val _postsErrorSharedFlow = MutableSharedFlow<Throwable?>()
    val postsErrorSharedFlow: SharedFlow<Throwable?> = _postsErrorSharedFlow

    private val _loadingStateFlow = MutableStateFlow(true)
    val loadingStateFlow: StateFlow<Boolean> = _loadingStateFlow


    fun getPosts() {
        viewModelScope.launch {
            try {
                getPostsUseCase.execute().collect {
                    _loadingStateFlow.emit(false)
                    _postsSharedFlow.emit(it)
                    _postsErrorSharedFlow.emit(null)

                }

            } catch (e: Exception) {
                _loadingStateFlow.emit(false)
                _postsErrorSharedFlow.emit(e)
            }

        }
    }

}