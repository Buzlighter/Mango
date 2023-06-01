package com.test.mango.auth.ui.di

import android.content.Context
import android.content.SharedPreferences
import com.test.mango.auth.ui.AuthActivity
import com.test.mango.data.di.component.AppComponent
import com.test.mango.data.di.scopes_and_qualifiers.ActivityScope
import com.test.mango.data.di.scopes_and_qualifiers.Application
import com.test.mango.registration.RegistrationActivity
import com.test.mango.registration.di.RegistrationComponent
import dagger.Component

@ActivityScope
@Component(
    dependencies = [AppComponent::class]
)
interface AuthComponent {

    @Application
    fun appContext(): Context

    fun getEncryptedSharedPref(): SharedPreferences

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder

        fun build(): AuthComponent
    }

    fun inject(authActivity: AuthActivity)
}