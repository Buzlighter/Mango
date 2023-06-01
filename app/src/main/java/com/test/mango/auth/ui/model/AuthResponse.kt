package com.test.mango.auth.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthResponse(val isSuccess: Boolean): Parcelable
