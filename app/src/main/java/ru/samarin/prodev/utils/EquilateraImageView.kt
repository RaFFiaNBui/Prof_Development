package ru.samarin.prodev.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class EquilateraImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    deffStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, deffStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}