package com.test.mango.data.api

import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthCheckBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.registration.model.RegisterResponse
import com.test.mango.registration.model.RegistrationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MangoService {
    @POST("api/v1/users/register/")
    suspend fun register(@Body registrationBody: RegistrationBody): Response<RegisterResponse>

    @POST("/api/v1/users/send-auth-code/")
    suspend fun auth(@Body authBody: AuthBody): Response<AuthResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthUser(@Body authCheckBody: AuthCheckBody): Response<AuthTokenResponse>
}