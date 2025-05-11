package com.zain.giftcard.models

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class ScratchView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var scratchBitmap: Bitmap? = null
    private var scratchCanvas: Canvas? = null

    private val eraserPath = Path()
    private val eraserPaint = Paint().apply {
        isAntiAlias = true
        isDither = true
        color = Color.TRANSPARENT
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 70f
    }

    private var revealListener: ((Float) -> Unit)? = null

    init {
        setLayerType(LAYER_TYPE_HARDWARE, null)
    }

    fun setRevealListener(listener: (Float) -> Unit) {
        revealListener = listener
    }

    fun reveal() {
        scratchCanvas?.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        invalidate()
    }

    private fun calculateScratchedPercent(): Float {
        val bitmap = scratchBitmap ?: return 0f
        var count = 0
        val pixels = IntArray(bitmap.width * bitmap.height)
        bitmap.getPixels(pixels, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

        for (pixel in pixels) {
            if (pixel == 0) count++
        }

        return count.toFloat() / pixels.size
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        scratchBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        scratchCanvas = Canvas(scratchBitmap!!)
        scratchCanvas?.drawColor(Color.GRAY)
    }

    override fun onDraw(canvas: Canvas) {
        scratchBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, null)
            canvas.drawPath(eraserPath, eraserPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                eraserPath.moveTo(event.x, event.y)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                eraserPath.lineTo(event.x, event.y)

                scratchCanvas?.drawPath(eraserPath, eraserPaint)
                eraserPath.reset()
                eraserPath.moveTo(event.x, event.y)

                invalidate()

                val percent = calculateScratchedPercent()
                revealListener?.invoke(percent)
            }
        }
        return true
    }
}
