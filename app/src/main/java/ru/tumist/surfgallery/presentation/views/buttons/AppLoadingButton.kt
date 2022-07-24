package ru.tumist.surfgallery.presentation.views.buttons

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.ButtonLoadingBinding

class AppLoadingButton @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {
    private val binding = ButtonLoadingBinding.inflate(LayoutInflater.from(context), this)

    var title: String = ""
        set(value) {
            field = value
            binding.titleTv.text = value
        }

    var isLoading: Boolean = false
        set(value) {
            field = value
            binding.loadingPb.isVisible = value
            binding.titleTv.isVisible = !value
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.AppLoadingButton) {
            title = getString(R.styleable.AppLoadingButton_text) ?: ""
            isLoading = getBoolean(R.styleable.AppLoadingButton_is_loading, false)
        }
    }
}