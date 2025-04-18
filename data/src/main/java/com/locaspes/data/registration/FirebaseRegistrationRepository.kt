package com.locaspes.data.registration

import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRegistrationRepository @Inject constructor(
    private val userDataRepository: UserDataRepository) : RegistrationRepository {

    private val dataBase = Firebase.firestore

    override suspend fun signUp(email: String, username: String, password: String): Boolean{
        //TODO: БЕЗОПАСНОСТЬ СДЕЛАТЬ
        try {

//            val newUser = UserProfile(
//                username = username,
//                password = password,
//                email = email,
//                premium = false,
//                projectsApplications = emptyList(),
//                projectsCreated = emptyList(),
//                projectsAccepted = emptyList(),
//
//            )
            val newUser = UserProfile(
                username = username,
                password = password,
                email = email,
                )
            val document = dataBase.collection("Users").add(newUser).await()
            document.update("id", document.id)
            Log.d("RegistrationRepository","user added with ID: ${document.id}")
            userDataRepository.saveUserId(document.id)
            return true
        }

        catch (e: Exception){
            println("Ошибка добавления пользователя: ${e.message}")
            return false
        }
    }

    override suspend fun signIn(emailOrUsername: String, password: String): Boolean {
        try {

            val emailSearchResult = dataBase.collection("Users")
                .whereEqualTo("email", emailOrUsername).
                get().
                await()

            if (!emailSearchResult.isEmpty){
                val document = emailSearchResult.documents.first()
                val storedPassword = document.getString("password")

                if (storedPassword == password){
                    userDataRepository.saveUserId(document.getString("id").toString())
                    return true
                }
            }
            val usernameSearchResult = dataBase.collection("Users")
                .whereEqualTo("username", emailOrUsername)
                .get()
                .await()

            if (!usernameSearchResult.isEmpty){
                val document = usernameSearchResult.documents.first()
                val storedPassword = document.getString("password")
                if (storedPassword == password){
                    userDataRepository.saveUserId(document.getString("id").toString())
                    return true
                }
            }
            return false
        }
        catch (e: Exception){
            throw e
        }
    }
}