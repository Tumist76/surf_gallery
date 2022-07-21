package ru.tumist.surfgallery

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import ru.tumist.surfgallery.service.di.appModule
import ru.tumist.surfgallery.service.di.localDataModule
import ru.tumist.surfgallery.service.di.repositoryModule
import ru.tumist.surfgallery.service.di.viewModelModule

class GalleryApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@GalleryApplication)
            modules(listOf(appModule, localDataModule, repositoryModule, viewModelModule))
        }

    }
}