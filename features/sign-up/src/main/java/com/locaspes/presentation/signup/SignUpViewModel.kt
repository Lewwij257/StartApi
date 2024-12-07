package com.locaspes.presentation.signup

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.locaspes.domain.entities.SignUpData
import com.locaspes.domain.repositories.SignUpRepository
import com.locaspes.presentation.SignUpRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository,
    private val router: SignUpRouter): ViewModel(){

    val correctEmailState = MutableLiveData<Boolean>()
    val correctUsernameState = MutableLiveData<Boolean>()
    val correctPasswordsState = MutableLiveData<Boolean>()
    val passwordEqualsState = MutableLiveData<Boolean>()
    val workInProgressState = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val usersLocalCollection = signUpRepository.getCollection()
    val signUpAvailabilityState = MutableLiveData<Boolean>()

    fun checkUsernameInput(username: String){
        correctUsernameState.value = (!TextUtils.isEmpty(username) && username.length > 3)
                &&
                !(usersLocalCollection.any { document ->
                    document["username"] == username
                })
    }
    fun checkEmailInput(email: String){
        correctEmailState.value = (!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                &&
                !(usersLocalCollection.any { document ->
                    document["email"] == email
                }))
    }
    fun checkPasswordInput(password1: String, password2: String) {
        correctPasswordsState.value = (password1.length > 5)
        passwordEqualsState.value = (password1 == password2)
    }
    fun checkSignUpAvailability(){
        try {
            signUpAvailabilityState.value =
                (correctUsernameState.value!! && correctEmailState.value!! && correctPasswordsState.value!! && passwordEqualsState.value!!)
        }
        catch (e: Exception){
            signUpAvailabilityState.value = false
        }}

    fun signUp(username: String, email: String, password1: String) {
        viewModelScope.launch {
            val result = signUpRepository.signUp(SignUpData(
                username, email, password1, false, "", 0
            ))

            when (result){
                true -> {
                    //TODO
                }
                false -> {
                    errorMessage.value = "Похоже, такой аккаунт уже есть!\n(попробуйте изменить имя пользователя или емайл)"
                }
            }
        }
    }


}