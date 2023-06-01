package com.test.mango.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.mango.data.cache.MangoRepository
import javax.inject.Inject

class ProfileViewModelFactory @Inject constructor(
    private val mangoRepository: MangoRepository
    ): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProfileViewModel(mangoRepository) as T
    }
}