package com.orzechowski.aidme.tools

import android.content.Context
import java.io.File

class AssetFinder
{
    private val soundExtensions: List<String> = listOf(".m4a", ".mp3") //type 0 = sound
    private val videoExtensions: List<String> = listOf(".mov", ".mp4") //type 1 = video
    private val imageExtensions: List<String> = listOf(".jpg", ".jpeg", ".png") //type 2 = image

    fun getFileFromAssets(context: Context, fileName: String, attempt: Int, type: Int): File? {
        val fullPath: String = when (type) {
            0 -> {
                if (attempt == soundExtensions.size) return null
                fileName + soundExtensions[attempt]
            }
            1 -> {
                if (attempt == videoExtensions.size) return null
                fileName + videoExtensions[attempt]
            }
            2 -> {
                if (attempt == imageExtensions.size) return null
                fileName + imageExtensions[attempt]
            }
            else -> return null
        }
        return File(context.cacheDir, fullPath).also {
            if (!it.exists()) {
                it.outputStream().use { cache ->
                    context.assets.open(fullPath).use { inputStream ->
                        inputStream.copyTo(cache)
                    }
                }
            }
        }
    }
}