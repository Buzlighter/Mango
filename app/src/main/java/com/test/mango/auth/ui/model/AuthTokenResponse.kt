package com.test.mango.auth.ui.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthTokenResponse(@SerializedName("refresh_token") val refreshToken: String,
                             @SerializedName("access_token") val accessToken: String,
                             @SerializedName("user_id") val userId: Int,
                             @SerializedName("is_user_exists") val isUserExist: Boolean): Parcelable
