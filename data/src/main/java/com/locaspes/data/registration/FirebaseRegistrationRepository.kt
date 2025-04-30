package com.locaspes.data.registration

import android.net.http.NetworkException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.rpc.context.AttributeContext.Auth
import com.locaspes.data.UserDataRepository
import com.locaspes.data.UserDataStore
import com.locaspes.data.model.AuthResult
import com.locaspes.data.model.UserProfile
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRegistrationRepository @Inject constructor(
    private val userDataRepository: UserDataRepository) : RegistrationRepository {

    private val dataBase = Firebase.firestore

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun signUp(userProfile: UserProfile): AuthResult{
        //TODO: БЕЗОПАСНОСТЬ СДЕЛАТЬ
        return try {
            val uniqueEmailQuery = dataBase.collection("Users")
                .whereEqualTo("email", userProfile.email)
                .get()
                .await()
            if (!uniqueEmailQuery.isEmpty){
                AuthResult.AuthenticationError("Email уже занят!")
            }
            val uniqueUsernameQuery = dataBase.collection("Users")
                .whereEqualTo("password", userProfile.password)
                .get()
                .await()
            if (!uniqueUsernameQuery.isEmpty){
                AuthResult.AuthenticationError("Имя пользователя уже занято!")
            }
            val document = dataBase.collection("Users").add(userProfile).await()
            document.update("id", document.id)
            userDataRepository.saveUserId(document.id)
            userDataRepository.saveUserName(userProfile.username)
            AuthResult.Success(document.id)
        }
        catch (e: Exception){
            when(e){
                is NetworkException -> AuthResult.NetworkError("Нет сети!")
                else -> AuthResult.UnknownError("Неизвестная ошибка: ${e.message}")

            }
        }
    }

    override suspend fun signIn(emailOrUsername: String, password: String): Result<String> {
        return try {
            val emailSearchResult = dataBase.collection("Users")
                .whereEqualTo("email", emailOrUsername).
                get().
                await()

            if (!emailSearchResult.isEmpty){
                val document = emailSearchResult.documents.first()
                val storedPassword = document.getString("password")

                if (storedPassword == password){
                    userDataRepository.saveUserId(document.getString("id").toString())
                    Result.success("Успех!")
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
                    Result.success("Успех!")
                }
            }
            Result.failure(Exception("Что-то пошло не так!"))
        }
        catch (e: Exception){
            Result.failure(Exception("Ошибка: ${e.message}"))
        }
    }
}