package com.locaspes.data

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseAuthRepository: AuthRepository {

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