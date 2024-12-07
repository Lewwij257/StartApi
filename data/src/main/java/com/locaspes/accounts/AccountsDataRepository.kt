package com.locaspes.accounts

import android.content.Context
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.accounts.entities.AccountDataEntity
import com.locaspes.accounts.sources.AccountsDataRepository
import com.locaspes.local.UserSharedPreferences
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountsDataRepository @Inject constructor(): AccountsDataRepository {

    private var database = FirebaseFirestore.getInstance()
    private var userCollectionRef = database.collection("Users")
    private var userDocumentsRef = userCollectionRef.document()



    override fun getAccountData() {
        TODO("Not yet implemented")
    }

    override fun setAccountUsername() {
        TODO("Not yet implemented")
    }

    override fun setAccountPassword() {
        TODO("Not yet implemented")
    }

    override fun setAccountEmail() {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(usernameOrEmail: String, password: String) {
        if (checkIfAccountExists(usernameOrEmail, usernameOrEmail)){

            //TODO
            //open the main activity with login data for current user

        }
    }

    override suspend fun signUp(newAccountData: AccountDataEntity): Boolean {
        if (!checkIfAccountExists(newAccountData.username, newAccountData.email)) {
            userDocumentsRef.set(newAccountData)
            return true
        } else {
            return false
        }
    }

    /**
     * @return true if this username or email already exists in database, false if not
     */
    override suspend fun checkIfAccountExists(username: String, email: String): Boolean {
        val usernameQuery = userCollectionRef.whereEqualTo("username", username).get().await()
        val emailQuery = userCollectionRef.whereEqualTo("email", email).get().await()
        return !(usernameQuery.isEmpty && emailQuery.isEmpty)
    }


    override fun getCollection(): MutableList<Map<String, Any>> {

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Users")
        val localCollection = mutableListOf<Map<String, Any>>()

        collectionRef
            .get()
            .addOnSuccessListener{ querySnapshot ->
                querySnapshot.documents.forEach{document ->
                    localCollection.add(document.data!!)
                }
            }
            .addOnFailureListener {
                Log.d("AccountsDataRepositoryLog", "getCollection, collectionRef.get() FAILED")
            }
        return localCollection
    }

    override fun updateLocalDatabase() {
        database = FirebaseFirestore.getInstance()
        userCollectionRef = database.collection("Users")
        userDocumentsRef = userCollectionRef.document()
    }





}