package com.locaspes.utils

import android.text.TextUtils

class InputValidator {

    fun checkEmailInput(email: String): Boolean{
        return !TextUtils.isEmpty(email)
                && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun checkUsernameInput(username: String): Boolean{
        return !TextUtils.isEmpty(username) && username.length > 3
    }

    fun checkPasswordInput(password: String): Boolean{
        return password.length > 5
    }

    fun validateFields(email: String, username: String, password: String): Boolean{
        return checkEmailInput(email) && checkUsernameInput(username) && checkPasswordInput(password)
    }

}