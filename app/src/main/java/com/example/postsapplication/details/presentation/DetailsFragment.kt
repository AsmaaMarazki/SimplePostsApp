package com.example.postsapplication.details.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.postsapplication.R
import com.example.postsapplication.databinding.FragmentDetailsBinding
import com.example.postsapplication.databinding.LayoutErrorBinding
import com.example.postsapplication.databinding.LayoutLoadingBinding
import com.example.postsapplication.databinding.LayoutPostBinding
import com.example.postsapplication.posts.domain.model.PostInfoModel
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private var _postBinding: LayoutPostBinding? = null
    private val postBinding get() = _postBinding!!

    private var _errorBinding: LayoutErrorBinding? = null
    private val errorBinding get() = _errorBinding!!

    private var _loadingBinding: LayoutLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!

    private var postId: Int? = null

    private val commentsViewModel: CommentsViewModel by lazy { ViewModelProvider(this)[CommentsViewModel::class.java] }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigateUp()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        _errorBinding = LayoutErrorBinding.bind(binding.root)
        _loadingBinding = LayoutLoadingBinding.bind(binding.root)
        _postBinding = LayoutPostBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val post = getPost()
        handlePostDetails(post)
        postId = post?.id
        getComments()
        collectLoading()
        collectError()
        collectComments()
    }

    private fun getComments() {
        postId?.let {
            commentsViewModel.getComments(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getPost(): PostInfoModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(POST_DETAILS, PostInfoModel::class.java)
        } else arguments?.getParcelable(POST_DETAILS)

    }

    private fun handlePostDetails(post: PostInfoModel?) {
        post?.run {
            postId = id
            with(postBinding) {
                tvUserName.text = userName
                tvPostTitle.text = title
                tvPostContent.text = content
            }
        }

        postBinding.groupPostDetail.isVisible = post != null
    }

    private fun collectLoading() {
        lifecycleScope.launch {
            commentsViewModel.loadingStateFlow.collect {
                loadingBinding.pbLoading.isVisible = it
            }
        }
    }

    private fun collectError() {
        lifecycleScope.launch {
            commentsViewModel.commentsErrorSharedFlow.collect {
                errorBinding.groupError.isVisible = it != null
                if (it != null) {
                    errorBinding.tvTryAgain.setOnClickListener {
                        getComments()
                    }
                }
            }
        }
    }

    private fun collectComments() {
        lifecycleScope.launch {
            commentsViewModel.commentsFlow.collect {
                if (it.isEmpty())
                    Toast.makeText(context, getString(R.string.no_comments), Toast.LENGTH_LONG)
                        .show()
                else {
                    binding.rvComments.adapter = CommentsAdapter(it)
                }
                binding.groupComments.isVisible = it.isNotEmpty()

            }
        }
    }

    companion object {
        const val POST_DETAILS = "POST_DETAILS"
    }

}