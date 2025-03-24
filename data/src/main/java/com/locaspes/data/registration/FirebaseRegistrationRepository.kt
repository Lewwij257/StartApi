package com.locaspes.data.registration

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseRegistrationRepository: RegistrationRepository {

    private val dataBase = Firebase.firestore

    override suspend fun signUp(email: String, username: String, password: String): Boolean{
        //TODO: БЕЗОПАСНОСТЬ СДЕЛАТЬ
        try {
            val newUser = hashMapOf(
                "username" to username,
                "password" to password,
                "email" to email,
                "premium" to false
            )
            val documentReference = dataBase.collection("Users").add(newUser).await()
            Log.d("RegistrationRepository","user added with ID: ${documentReference.id}")
            println("Пользователь добавлен с ID: ${documentReference.id}")
            return true
        }
        catch (e: Exception){
            println("Ошибка добавления пользователя: ${e.message}")
            return false
        }
    }



    override suspend fun signIn(emailOrUsername: String, password: String): Boolean {
        TODO("Not yet implemented")
    }


}