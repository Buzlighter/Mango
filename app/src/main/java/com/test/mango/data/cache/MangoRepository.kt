package com.test.mango.data.cache

import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthCheckBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.registration.model.RegisterResponse
import com.test.mango.registration.model.RegistrationBody
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface MangoRepository {
    fun register(registrationBody: RegistrationBody): Flow<Response<RegisterResponse>>

    fun auth(authBody: AuthBody): Flow<Response<AuthResponse>>

    fun authCheck(authCheckBody: AuthCheckBody): Flow<Response<AuthTokenResponse>>
}