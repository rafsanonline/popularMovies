package com.assignment.self
import android.content.Context
import com.assignment.self.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MyApp : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    lateinit var context: Context


    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        context = base
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}
