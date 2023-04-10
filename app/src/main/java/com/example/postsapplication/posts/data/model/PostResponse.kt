package com.example.postsapplication.posts.data.model

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("user_id") var userId: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("body") var content: String? = null

)