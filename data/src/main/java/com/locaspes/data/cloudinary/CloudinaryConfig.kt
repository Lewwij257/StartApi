package com.locaspes.data.cloudinary

import android.content.Context
import com.cloudinary.android.MediaManager
import java.util.Properties

object CloudinaryConfig {
    private var isInitialized = false

    fun initialize(context: Context) {
        if (!isInitialized) {
            // Чтение файла Secretiki
            val properties = Properties()
            try {
                context.assets.open("Secretiki").use { inputStream ->
                    properties.load(inputStream)
                }
            } catch (e: Exception) {
                throw RuntimeException("Failed to load Secretiki file", e)
            }

            // Извлечение ключей из файла
            val config = hashMapOf(
                "cloud_name" to properties.getProperty("CLOUDINARY_CLOUD_NAME"),
                "api_key" to properties.getProperty("CLOUDINARY_API_KEY"),
                "api_secret" to properties.getProperty("CLOUDINARY_API_SECRET")
            )

            // Проверка, что ключи не пустые
            config.forEach { (key, value) ->
                if (value.isNullOrEmpty()) {
                    throw RuntimeException("Missing or empty $key in Secretiki file")
                }
            }

            // Инициализация MediaManager
            MediaManager.init(context, config)
            isInitialized = true
        }
    }
}