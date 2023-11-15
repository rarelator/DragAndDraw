package com.example.draganddraw

import android.content.Context
import android.util.AttributeSet
import android.view.View

data class BoxDrawingView(
    val context: Context,
    val attrs: AttributeSet? = null
): View(context, attrs)
