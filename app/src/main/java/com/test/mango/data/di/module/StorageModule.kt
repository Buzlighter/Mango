package com.test.mango.data.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.test.mango.data.di.scopes_and_qualifiers.AppScope
import com.test.mango.data.di.scopes_and_qualifiers.Application
import dagger.Module
import dagger.Provides

private const val SECRET_STORAGE_NAME = "encrypted_preferences"

@Module
object StorageModule {

    @AppScope
    @Provides
    fun provideEncryptedSharedPref(@Application context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            SECRET_STORAGE_NAME,
            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}