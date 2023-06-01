package com.test.mango.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.mango.data.cache.MangoRepository
import com.test.mango.data.model.Status
import com.test.mango.profile.model.ProfileData
import com.test.mango.profile.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProfileViewModel(private val mangoRepository: MangoRepository) : ViewModel() {
    val userFlow: MutableStateFlow<Status<User>> = MutableStateFlow(Status.StateLess())


    fun getUser(token: String) = viewModelScope.launch {
        mangoRepository
            .getUser(token)
            .flowOn(Dispatchers.IO)
            .catch {
                userFlow.value = Status.Error(it.message!!) }
            .collect { response ->
                if (response.isSuccessful) {
                    userFlow.value = Status.Success(response.body()!!)
                } else if (response.isSuccessful.not()) {
                    userFlow.value = Status.Error(response.errorBody().toString())
                } else {
                    userFlow.value = Status.Loading()
                }
            }
    }
}