package com.test.mango.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class RegisterResponse(
    @SerializedName("refresh_token")
    val accessToken: String,
    @SerializedName("access_token")
    val refreshToken: String,
    @SerializedName("user_id")
    val  userId: Int
    ): Parcelable