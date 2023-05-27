package com.test.mango.data.di.module

import com.test.mango.data.cache.MangoRepository
import com.test.mango.data.cache.MangoRepositoryImpl
import com.test.mango.data.di.scopes_and_qualifiers.AppScope
import dagger.Binds
import dagger.Module

@Module
interface CacheModule {

    @AppScope
    @Binds
    fun bindMangoRepository(mangoRepositoryImpl: MangoRepositoryImpl): MangoRepository
}