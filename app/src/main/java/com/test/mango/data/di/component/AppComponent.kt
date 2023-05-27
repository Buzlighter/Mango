package com.test.mango.data.di.component

import android.content.Context
import android.content.SharedPreferences
//import com.test.mango.registration.di.RegistrationComponent
import dagger.BindsInstance
import dagger.Component
import com.test.mango.App
import com.test.mango.data.api.MangoService
import com.test.mango.data.cache.MangoRepository
import com.test.mango.data.di.module.CacheModule
import com.test.mango.data.di.module.NetworkModule
import com.test.mango.data.di.module.StorageModule
import com.test.mango.data.di.scopes_and_qualifiers.AppScope
import com.test.mango.data.di.scopes_and_qualifiers.Application

@AppScope
@Component(modules = [NetworkModule::class, CacheModule::class, StorageModule::class])
interface AppComponent {

    @Application
    fun appContext(): Context

    fun provideMangoService(): MangoService

    fun provideMangoRepository(): MangoRepository

    fun provideSharedPref(): SharedPreferences

    @Component.Builder
    interface Builder {

        fun appContext(@BindsInstance
                       @Application
                       context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}