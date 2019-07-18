package se.mobility46.m46card

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

class CardSide(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    init {
        background = context.resources.getDrawable(R.drawable.rectangle, null)
    }

}