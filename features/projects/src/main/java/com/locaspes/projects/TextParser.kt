package com.locaspes.projects

object TextParser {
    fun parseString(string: String, delimiters: Regex = Regex("[,\\s]+")): List<String>{
        return string.lowercase().split(delimiters).filter { it.isNotBlank() }
    }
}