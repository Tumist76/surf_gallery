package ru.tumist.surfgallery.service

import ru.tumist.surfgallery.domain.model.AuthInfo

class ApplicationState {
    var authInfo: AuthInfo? = null

    var onUnauthenticated : () -> Unit = {}
}