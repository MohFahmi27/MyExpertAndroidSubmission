package com.mfahmi.myexpertandroidsubmission

import android.app.Application
import com.mfahmi.core.di.databaseModule
import com.mfahmi.core.di.networkModule
import com.mfahmi.core.di.repositoryModule
import com.mfahmi.myexpertandroidsubmission.di.useCaseModule
import com.mfahmi.myexpertandroidsubmission.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}