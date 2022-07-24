package ru.tumist.surfgallery.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.isVisible
import ru.tumist.surfgallery.R
import ru.tumist.surfgallery.databinding.ButtonLoadingBinding
import ru.tumist.surfgallery.databinding.ProfileFieldBinding

class ProfileField @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {
    private val binding = ProfileFieldBinding.inflate(LayoutInflater.from(context), this)

    var fieldLabel: String = ""
        set(value) {
            field = value
            binding.fieldLabel.text = value
        }

    var fieldValue: String = ""
        set(value) {
            field = value
            binding.fieldValue.text = value
        }

    init {
        context.withStyledAttributes(attributeSet, R.styleable.ProfileField) {
            fieldLabel = getString(R.styleable.ProfileField_label) ?: ""
            fieldValue = getString(R.styleable.ProfileField_value) ?: ""
        }
    }
}