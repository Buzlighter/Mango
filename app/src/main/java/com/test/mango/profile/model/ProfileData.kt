package com.test.mango.profile.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileData(@SerializedName("name") val name: String,
                       @SerializedName("username") val username: String,
                       @SerializedName("city") val city: String,
                       @SerializedName("birthday") val birthday: String,
                       @SerializedName("avatar") val avatar: String?,
                       @SerializedName("phone") val phone: String): Parcelable
