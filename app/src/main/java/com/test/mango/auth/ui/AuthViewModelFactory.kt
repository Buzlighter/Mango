package com.test.mango.auth.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mango.data.cache.MangoRepository
import javax.inject.Inject

class AuthViewModelFactory @Inject constructor(private val mangoRepository: MangoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(mangoRepository) as T
    }
}