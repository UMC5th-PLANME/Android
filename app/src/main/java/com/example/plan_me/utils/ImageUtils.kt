package com.example.plan_me.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.File
import java.io.FileOutputStream

object ImageUtils {
    fun bitmapToFile(context: Context, bitmap: Bitmap): File {
        val filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File(filesDir, "profile_image.jpg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
        return file
    }
}