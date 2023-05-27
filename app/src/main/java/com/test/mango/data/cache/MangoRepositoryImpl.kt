package com.test.mango.data.cache

import com.test.mango.data.api.MangoService
import com.test.mango.data.model.RegisterResponse
import com.test.mango.data.model.RegistrationBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MangoRepositoryImpl @Inject constructor(private val mangoService: MangoService): MangoRepository {

    override fun register(registrationBody: RegistrationBody) = flow {
        emit(mangoService.register(registrationBody))
    }

}