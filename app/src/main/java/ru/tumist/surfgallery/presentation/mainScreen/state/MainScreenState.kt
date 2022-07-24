package ru.tumist.surfgallery.presentation.mainScreen.state

import ru.tumist.surfgallery.domain.model.PictureModel

sealed interface MainScreenState {
    data class Loaded(val pictures: List<PictureModel>) : MainScreenState
    object Loading : MainScreenState
    object Empty : MainScreenState
    object Error : MainScreenState
    object Init : MainScreenState
}
