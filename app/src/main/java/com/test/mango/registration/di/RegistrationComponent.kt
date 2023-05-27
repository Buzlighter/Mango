package com.test.mango.registration.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import com.test.mango.data.di.component.AppComponent
import com.test.mango.data.di.scopes_and_qualifiers.ActivityScope
import com.test.mango.data.di.scopes_and_qualifiers.Application
import com.test.mango.registration.RegistrationActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class]
)
interface RegistrationComponent {

    @Application
    fun appContext(): Context

    fun getEncryptedSharedPref(): SharedPreferences

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): RegistrationComponent
    }

    fun inject(registrationActivity: RegistrationActivity)
}