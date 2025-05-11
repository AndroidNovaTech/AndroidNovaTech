package com.zain.giftcard.activity

class ScratchCardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var scratchBitmap: Bitmap? = null
    private var scratchOverlayBitmap: Bitmap? = null

    init {
        paint.isAntiAlias = true
        paint.color = Color.GRAY
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (scratchBitmap != null) {
            canvas.drawBitmap(scratchBitmap!!, 0f, 0f, null)
        }

        if (scratchOverlayBitmap != null) {
            canvas.drawBitmap(scratchOverlayBitmap!!, 0f, 0f, null)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {
                path.lineTo(x, y)
                invalidate()
            }
        }
        return true
    }

    // Set Scratch Image
    fun setScratchImage(image: Bitmap) {
        scratchBitmap = image
        scratchOverlayBitmap = Bitmap.createBitmap(image.width, image.height, Bitmap.Config.ARGB_8888)
        scratchOverlayBitmap?.eraseColor(Color.GRAY) // Gray overlay for scratch effect
        invalidate()
    }

    // Reveal the content (to show the coupon image)
    fun revealCoupon(couponBitmap: Bitmap) {
        scratchBitmap = couponBitmap
        invalidate()
    }
}
