package com.example.postsapplication.posts.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.postsapplication.R
import com.example.postsapplication.databinding.FragmentPostsBinding
import com.example.postsapplication.databinding.LayoutErrorBinding
import com.example.postsapplication.databinding.LayoutLoadingBinding
import kotlinx.coroutines.launch

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private var _errorBinding: LayoutErrorBinding? = null
    private val errorBinding get() = _errorBinding!!

    private var _loadingBinding: LayoutLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!

    private val postsViewModel: PostsViewModel by lazy { ViewModelProvider(this)[PostsViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        _errorBinding = LayoutErrorBinding.bind(binding.root)
        _loadingBinding = LayoutLoadingBinding.bind(binding.root)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsViewModel.getPosts()
        collectLoading()
        collectPosts()
        collectError()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun collectPosts() {
        lifecycleScope.launch {
            postsViewModel.postsFlow.collect {
                if (it.isEmpty())
                    Toast.makeText(context, getString(R.string.no_posts), Toast.LENGTH_LONG).show()
                else {
                    binding.rvPosts.adapter = PostsAdapter(it)
                    binding.rvPosts.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun collectError() {
        lifecycleScope.launch {
            postsViewModel.postsErrorSharedFlow.collect {
                errorBinding.run {
                    groupError.isVisible = it != null
                    if (it != null) {
                        tvTryAgain.setOnClickListener {
                            postsViewModel.getPosts()
                        }
                    }

                }
            }
        }
    }

    private fun collectLoading() {
        lifecycleScope.launch {
            postsViewModel.loadingStateFlow.collect {
                loadingBinding.pbLoading.isVisible = it
            }
        }
    }
}