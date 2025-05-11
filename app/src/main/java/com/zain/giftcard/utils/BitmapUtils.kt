package com.zain.giftcard.utils

import android.graphics.Bitmap
import java.nio.ByteBuffer


object BitmapUtils {
    /**
     * Compares two bitmaps and gives the percentage of similarity
     *
     * @param bitmap1 input bitmap 1
     * @param bitmap2 input bitmap 2
     * @return a value between 0.0 to 1.0 . Note the method will return 0.0 if either of bitmaps are null nor of same size.
     */
    fun compareEquivalance(bitmap1: Bitmap?, bitmap2: Bitmap?): Float {
        if (bitmap1 == null || bitmap2 == null || bitmap1.getWidth() != bitmap2.getWidth() || bitmap1.getHeight() != bitmap2.getHeight()) {
            return 0f
        }


        val buffer1 = ByteBuffer.allocate(bitmap1.getHeight() * bitmap1.getRowBytes())
        bitmap1.copyPixelsToBuffer(buffer1)

        val buffer2 = ByteBuffer.allocate(bitmap2.getHeight() * bitmap2.getRowBytes())
        bitmap2.copyPixelsToBuffer(buffer2)

        val array1 = buffer1.array()
        val array2 = buffer2.array()

        val len = array1.size // array1 and array2 will be of some length.
        var count = 0

        for (i in 0..<len) {
            if (array1[i] == array2[i]) {
                count++
            }
        }

        return ((count).toFloat()) / len
    }

    /**
     * Finds the percentage of pixels that do are empty.
     *
     * @param bitmap input bitmap
     * @return a value between 0.0 to 1.0 . Note the method will return 0.0 if either of bitmaps are null nor of same size.
     */
    fun getTransparentPixelPercent(bitmap: Bitmap?): Float {
        if (bitmap == null) {
            return 0f
        }

        val buffer = ByteBuffer.allocate(bitmap.getHeight() * bitmap.getRowBytes())
        bitmap.copyPixelsToBuffer(buffer)

        val array = buffer.array()

        val len = array.size
        var count = 0

        for (b in array) {
            if (b.toInt() == 0) {
                count++
            }
        }

        return ((count).toFloat()) / len
    }
}


