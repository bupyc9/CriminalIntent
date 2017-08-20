package ru.bupyc9.criminalintent

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

fun getScaledBitmap(path: String, destWidth: Int, destHeight: Int): Bitmap {
    var options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeFile(path, options)
    val srcWidth = options.outWidth
    val srcHeight = options.outHeight
    var inSampleSize = 1

    if (srcHeight > destHeight || srcWidth > destWidth) {
        if (srcWidth > srcHeight) {
            inSampleSize = Math.round(srcHeight.toDouble() / destHeight).toInt()
        } else {
            inSampleSize = Math.round(srcWidth.toDouble() / destWidth).toInt()
        }
    }

    options = BitmapFactory.Options()
    options.inSampleSize = inSampleSize

    return BitmapFactory.decodeFile(path, options)
}

fun getScaledBitmap(path: String, activity: Activity): Bitmap {
    val size = Point()
    activity.windowManager.defaultDisplay.getSize(size)

    return getScaledBitmap(path, size.x, size.y)
}