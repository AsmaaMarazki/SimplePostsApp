package com.example.postsapplication.details.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.postsapplication.databinding.ItemCommentViewBinding
import com.example.postsapplication.details.domain.model.CommentModel

class CommentsAdapter(private val comments: List<CommentModel>) :
    RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val binding =
            ItemCommentViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    class CommentViewHolder(
        private val binding: ItemCommentViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentModel) {
            binding.run {
                tvComment.text = comment.body
                tvName.text = comment.name
            }
        }
    }
}