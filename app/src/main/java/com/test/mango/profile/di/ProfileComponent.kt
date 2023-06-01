package com.test.mango.profile.di

import android.content.Context
import android.content.SharedPreferences
import com.test.mango.auth.ui.AuthActivity
import com.test.mango.auth.ui.di.AuthComponent
import com.test.mango.data.di.component.AppComponent
import com.test.mango.data.di.scopes_and_qualifiers.ActivityScope
import com.test.mango.data.di.scopes_and_qualifiers.Application
import com.test.mango.profile.ProfileActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [AppComponent::class])
interface ProfileComponent {

    @Application
    fun appContext(): Context

    fun getEncryptedSharedPref(): SharedPreferences

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent): Builder
        fun build(): ProfileComponent
    }

    fun inject(profileActivity: ProfileActivity)
}