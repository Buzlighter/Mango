package com.test.mango.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mango.data.cache.MangoRepository
import com.test.mango.data.model.RegisterResponse
import com.test.mango.data.model.RegistrationBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOn
import com.test.mango.data.model.Status
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch


class RegistrationViewModel(private val mangoRepository: MangoRepository): ViewModel() {
    val stateFlow: MutableStateFlow<Status<RegisterResponse>> = MutableStateFlow(Status.Loading())

    fun registerUser(registrationBody: RegistrationBody) = viewModelScope.launch {
        mangoRepository
            .register(registrationBody)
            .flowOn(Dispatchers.IO)
            .catch {
                    stateFlow.value = Status.Error(it.message!!)
            }
            .collect { response ->
                if (response.isSuccessful) {
                    stateFlow.value = Status.Success(response.body()!!)
                } else if (response.isSuccessful.not()) {
                        stateFlow.value = Status.Error(response.errorBody().toString())
                } else {
                        stateFlow.value = Status.Loading()
                }
            }
    }
}