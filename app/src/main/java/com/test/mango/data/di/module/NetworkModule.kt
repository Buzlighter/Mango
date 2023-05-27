package com.test.mango.data.di.module

import com.test.mango.data.api.MangoService
import com.test.mango.data.di.scopes_and_qualifiers.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_MANGO_URL = "https://plannerok.ru/"

@Module
class NetworkModule {

    @AppScope
    @Provides
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @AppScope
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_MANGO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AppScope
    @Provides
    fun provideMangoService(retrofit: Retrofit): MangoService {
        return retrofit.create(MangoService::class.java)
    }
}