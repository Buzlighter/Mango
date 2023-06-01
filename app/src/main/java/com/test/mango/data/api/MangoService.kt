package com.test.mango.data.api

import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthCheckBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.profile.model.User
import com.test.mango.registration.model.RegisterResponse
import com.test.mango.registration.model.RegistrationBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface MangoService {
    @POST("api/v1/users/register/")
    suspend fun register(@Body registrationBody: RegistrationBody): Response<RegisterResponse>

    @POST("/api/v1/users/send-auth-code/")
    suspend fun auth(@Body authBody: AuthBody): Response<AuthResponse>

    @POST("/api/v1/users/check-auth-code/")
    suspend fun checkAuthUser(@Body authCheckBody: AuthCheckBody): Response<AuthTokenResponse>


    @GET("/api/v1/users/me/")
    suspend fun getUser(@Header("Authorization") authorization: String): Response<User>
}