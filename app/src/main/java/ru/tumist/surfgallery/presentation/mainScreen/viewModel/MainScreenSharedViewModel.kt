package ru.tumist.surfgallery.presentation.mainScreen.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ru.tumist.surfgallery.domain.model.PictureModel
import ru.tumist.surfgallery.domain.model.ResultModel
import ru.tumist.surfgallery.domain.repository.AuthInfoRepository
import ru.tumist.surfgallery.domain.useCase.ChangeFavoriteStatusUseCase
import ru.tumist.surfgallery.domain.useCase.ClearLocalDataUseCase
import ru.tumist.surfgallery.domain.useCase.GetPicturesUseCase
import ru.tumist.surfgallery.presentation.mainScreen.state.MainScreenState
import ru.tumist.surfgallery.presentation.splash.state.SplashScreenState
import ru.tumist.surfgallery.service.ApplicationState

class MainScreenSharedViewModel(
    private val getPicturesUseCase: GetPicturesUseCase,
    private val changeFavoriteStatusUseCase: ChangeFavoriteStatusUseCase,
    private val clearLocalDataUseCase: ClearLocalDataUseCase,
) : ViewModel() {
    val state = MutableStateFlow<MainScreenState>(MainScreenState.Init)

    private var picturesList = listOf<PictureModel>()

    fun init() {
        if (state.value is MainScreenState.Init) getPictures()
    }

    fun getPictures() {
        viewModelScope.launch {
            state.emit(MainScreenState.Loading(picturesList))
            when (val result = getPicturesUseCase.execute()) {
                is ResultModel.Success -> {
                    if (result.value.isEmpty()) {
                        state.emit(MainScreenState.Empty)
                    } else {
                        picturesList = result.value
                        state.emit(MainScreenState.Loaded(result.value))
                    }
                }
                else -> {
                    if (picturesList.isNotEmpty()) {
                        state.emit(
                            MainScreenState.Loaded(picturesList, isCompletedWithError = true)
                        )
                    } else {
                        state.emit(MainScreenState.Error)
                    }
                }

            }
        }
    }

    fun setFavorite(model: PictureModel, isFavorite: Boolean) {
        viewModelScope.launch {
            val result = changeFavoriteStatusUseCase.execute(picturesList, model.id, isFavorite)
            picturesList = result
            state.emit(MainScreenState.Loaded(picturesList))
        }
    }

    fun clearLocalData() {
        picturesList = listOf()
        viewModelScope.launch {
            clearLocalDataUseCase.execute()
        }
    }
}