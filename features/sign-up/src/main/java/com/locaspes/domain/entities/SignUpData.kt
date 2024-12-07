package com.locaspes.domain.entities

data class SignUpData(
    val username: String,
    val email: String,
    val password: String,
    val premium: Boolean = false,
    val premiumStarted: String,
    val projectAmount: Int
)
