package com.test.mango.data.cache

import com.test.mango.data.model.RegisterResponse
import com.test.mango.data.model.RegistrationBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

interface MangoRepository {
    fun register(registrationBody: RegistrationBody): Flow<Response<RegisterResponse>>
}