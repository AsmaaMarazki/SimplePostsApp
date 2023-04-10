package com.example.postsapplication.posts.domain.model

import android.os.Parcel
import android.os.Parcelable

data class PostModel(
    val id: Int? = null,
    val userId: Int? = null,
    val title: String? = null,
    val content: String? = null
)

data class PostInfoModel(
    val id: Int? = null,
    val userName: String? = null,
    val title: String? = null,
    val content: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(userName)
        parcel.writeString(title)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostInfoModel> {
        override fun createFromParcel(parcel: Parcel): PostInfoModel {
            return PostInfoModel(parcel)
        }

        override fun newArray(size: Int): Array<PostInfoModel?> {
            return arrayOfNulls(size)
        }
    }
}