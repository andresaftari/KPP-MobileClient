package com.kppmining.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object ImageCompressor {
    fun addTempFile(requireContext: Context, bitmap: Bitmap): File {
        val file = File(
            requireContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            System.currentTimeMillis().toString() + "_image.png"
        )

        val byteArrayStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 10, byteArrayStream)
        val bitmapdata: ByteArray = byteArrayStream.toByteArray()

        // Write the bytes in file
        try {
            val outputStream = FileOutputStream(file)

            outputStream.apply {
                write(bitmapdata)
                flush()
                close()
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }

        return file
    }
}