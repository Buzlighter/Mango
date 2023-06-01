package com.test.mango.profile.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(@SerializedName("profile_data") val profileData: ProfileData): Parcelable
