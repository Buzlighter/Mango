package com.test.mango.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mango.auth.ui.AuthViewModel
import com.test.mango.data.api.MangoService
import com.test.mango.data.cache.MangoRepository
import javax.inject.Inject

class RegistrationViewModelFactory @Inject constructor(
    private val mangoRepository: MangoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RegistrationViewModel(mangoRepository) as T
    }
}