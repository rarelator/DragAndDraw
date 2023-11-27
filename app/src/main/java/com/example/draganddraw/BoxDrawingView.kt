package com.example.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

private const val TAG = "BoxDrawingView"
private const val MAX_NUM_SHAPES = 3
class BoxDrawingView(
    context: Context,
    attrs: AttributeSet? = null
): View(context, attrs) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply{
        color=0x22ff0000.toInt()
    }
    private val backgroundPaint = Paint().apply{
        color=0xfff8efe0.toInt()
    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)
        val current = PointF(event.x, event.y)
        var action = ""

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    if (boxes.size < MAX_NUM_SHAPES) {
                        boxes.add(it)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }

            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }

            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEl"
                currentBox = null
            }
        }
        Log.d(TAG, "$action at x=${current.x}, y=${current.y}")
        Log.d(TAG, "height is ${currentBox?.height}, width is ${currentBox?.width}")
        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current

            val minimum = Math.min(it.height, it.width)
            it.end.x = it.start.x + minimum
            it.end.y = it.start.y + minimum
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)
        boxes.forEach{box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }
}
