package ru.tumist.surfgallery.presentation.views.pictures

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val verticalItemPadding: Int,
    private val horizontalItemPadding: Int,
    private val horizontalMargins: Int,
    private val spanCount: Int = 1,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            left = if (parent.getChildAdapterPosition(view) % spanCount != 0) {
                horizontalItemPadding
            } else {
                horizontalMargins
            }
            if (parent.getChildAdapterPosition(view) % spanCount == spanCount - 1) {
                right = horizontalMargins
            }
            bottom = verticalItemPadding
        }
    }
}