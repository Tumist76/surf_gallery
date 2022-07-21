package ru.tumist.surfgallery.service.di

import org.koin.dsl.module
import ru.tumist.surfgallery.service.ApplicationState

val appModule = module {
    single { ApplicationState() }
}