package com.test.mango.data.cache

import com.test.mango.data.api.MangoService
import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthCheckBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.registration.model.RegistrationBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class MangoRepositoryImpl @Inject constructor(private val mangoService: MangoService): MangoRepository {

    override fun register(registrationBody: RegistrationBody) = flow {
        emit(mangoService.register(registrationBody))
    }

    override fun auth(authBody: AuthBody): Flow<Response<AuthResponse>> = flow {
        emit(mangoService.auth(authBody))
    }

    override fun authCheck(authCheckBody: AuthCheckBody): Flow<Response<AuthTokenResponse>> = flow {
        emit(mangoService.checkAuthUser(authCheckBody))
    }
}