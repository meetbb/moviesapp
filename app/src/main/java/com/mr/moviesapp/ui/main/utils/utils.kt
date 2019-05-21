package com.mr.moviesapp.ui.main.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream

fun getByteArrayFrmBitmap(bitmap: Bitmap): ByteArray? {
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()
    return byteArray
}

fun getImageFromByte(image: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(image, 0, image.size)
}