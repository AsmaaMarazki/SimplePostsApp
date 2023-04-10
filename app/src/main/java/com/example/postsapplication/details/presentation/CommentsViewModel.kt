package com.example.postsapplication.details.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postsapplication.details.data.repository.CommentsRepositoryImpl
import com.example.postsapplication.details.domain.model.CommentModel
import com.example.postsapplication.details.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CommentsViewModel(
    private val getCommentsUseCase: GetCommentsUseCase = GetCommentsUseCase(
        CommentsRepositoryImpl()
    )
) : ViewModel() {
    private val _commentsSharedFlow = MutableSharedFlow<List<CommentModel>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val commentsFlow: Flow<List<CommentModel>> = _commentsSharedFlow.distinctUntilChanged()

    private val _commentsErrorSharedFlow = MutableSharedFlow<Throwable?>()
    val commentsErrorSharedFlow: SharedFlow<Throwable?> = _commentsErrorSharedFlow

    private val _loadingStateFlow = MutableStateFlow(true)
    val loadingStateFlow: StateFlow<Boolean> = _loadingStateFlow


    fun getComments(postId: Int) {
        viewModelScope.launch {
            try {
                val comments = getCommentsUseCase.execute(postId)
                _loadingStateFlow.emit(false)
                _commentsSharedFlow.emit(comments)
                _commentsErrorSharedFlow.emit(null)

            } catch (e: Exception) {
                _loadingStateFlow.emit(false)
                _commentsErrorSharedFlow.emit(e)

            }
        }
    }

}
