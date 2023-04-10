package com.example.postsapplication.posts.presentation

import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapplication.R
import com.example.postsapplication.databinding.ItemPostViewBinding
import com.example.postsapplication.databinding.LayoutPostBinding
import com.example.postsapplication.details.presentation.DetailsFragment.Companion.POST_DETAILS
import com.example.postsapplication.posts.domain.model.PostInfoModel

class PostsAdapter(private val posts: List<PostInfoModel>) :
    RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding =
            ItemPostViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val postBinding = LayoutPostBinding.bind(binding.root)
        return PostViewHolder(binding, postBinding)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    class PostViewHolder(
        private val binding: ItemPostViewBinding,
        private val postBinding: LayoutPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostInfoModel) {
            binding.cvPost.setOnClickListener {

                it.findNavController().navigate(
                    R.id.action_postsFragment_to_detailsFragment,
                    bundleOf(POST_DETAILS to post)
                )
            }
            postBinding.run {
                with(post) {
                    tvUserName.text = userName ?: root.context.getString(R.string.anonymous)
                    tvPostTitle.text = title
                    tvPostContent.text = content
                    groupPostDetail.visibility = VISIBLE

                }
            }
        }
    }
}