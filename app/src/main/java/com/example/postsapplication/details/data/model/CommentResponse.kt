package com.example.postsapplication.details.data.model

import com.google.gson.annotations.SerializedName


data class CommentResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("post_id") var postId: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("body") var body: String? = null
)