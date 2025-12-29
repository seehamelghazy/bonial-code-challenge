package com.bonial.codechallenge

import android.app.Application
import com.bonial.feature.brochures.di.featureBrochuresModule
import com.bonial.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BonialCodeChallengeApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BonialCodeChallengeApplication)
            modules(networkModule, featureBrochuresModule)
        }
    }
}
