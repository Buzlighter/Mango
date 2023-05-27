package com.test.mango.data.api

import com.test.mango.data.model.RegisterResponse
import com.test.mango.data.model.RegistrationBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MangoService {
    @POST("api/v1/users/register/")
    suspend fun register(@Body registrationBody: RegistrationBody): Response<RegisterResponse>
}