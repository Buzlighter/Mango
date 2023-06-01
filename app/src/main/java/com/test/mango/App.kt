package com.test.mango

import android.app.Application
import com.test.mango.data.di.component.AppComponent
import com.test.mango.data.di.component.DaggerAppComponent


class App: Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
       appComponent =  DaggerAppComponent.builder().appContext(this).build()
    }
}