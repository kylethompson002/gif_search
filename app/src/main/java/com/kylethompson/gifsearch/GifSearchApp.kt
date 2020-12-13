package com.kylethompson.gifsearch

import android.app.Application
import com.kylethompson.gifsearch.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import timber.log.Timber.DebugTree


class GifSearchApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(level = if (BuildConfig.DEBUG) Level.DEBUG else Level.NONE)
            androidContext(this@GifSearchApp)
            modules(appModule)
        }

        Timber.plant(DebugTree())
    }
}
