package com.test.mango.auth.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.viewModelScope
import com.test.mango.auth.ui.model.AuthBody
import com.test.mango.auth.ui.model.AuthCheckBody
import com.test.mango.auth.ui.model.AuthResponse
import com.test.mango.auth.ui.model.AuthTokenResponse
import com.test.mango.data.cache.MangoRepository
import com.test.mango.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response

class AuthViewModel(private val mangoRepository: MangoRepository) : ViewModel() {
    val authFlow: MutableStateFlow<Status<AuthResponse>> = MutableStateFlow(Status.StateLess())
    val checkAuthUserFlow: MutableStateFlow<Status<AuthTokenResponse>> = MutableStateFlow(Status.StateLess())

    private val hardCodedSmsNum = "133337"
    private var stopObserve = false

    fun authUser(authBody: AuthBody) = viewModelScope.launch {
        mangoRepository
            .auth(authBody)
            .flowOn(Dispatchers.IO)
            .catch { authFlow.value = Status.Error(it.message!!) }
            .collect { authResponseCollector(it, authBody) }
    }


    private fun authResponseCollector(response: Response<AuthResponse>, authBody: AuthBody) {
        if (response.isSuccessful) {
            checkUser(
                AuthCheckBody(authBody.phone, hardCodedSmsNum)
            )
            authFlow.value = Status.Success(response.body()!!)
        } else if (response.isSuccessful.not()) {
            authFlow.value = Status.Error(response.errorBody().toString())
        } else {
            authFlow.value = Status.Loading()
        }
    }

    fun checkUser(authCheckBody: AuthCheckBody) = viewModelScope.launch {
        mangoRepository
            .authCheck(authCheckBody)
            .flowOn(Dispatchers.IO)
            .catch { checkAuthUserFlow.value = Status.Error(it.message!!) }
            .collect {
                tokenResponseCollector(it) }
    }

    private fun tokenResponseCollector(response: Response<AuthTokenResponse>) {
        if (response.isSuccessful) {
            checkAuthUserFlow.value = Status.Success(response.body()!!)
        } else if (response.isSuccessful.not()) {
            checkAuthUserFlow.value = Status.Error(response.errorBody().toString())
        } else {
            checkAuthUserFlow.value = Status.Loading()
        }
    }

    fun stopObserve() {
        stopObserve = true
    }

}