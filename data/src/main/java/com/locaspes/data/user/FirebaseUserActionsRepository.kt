package com.locaspes.data.user

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirebaseUserActionsRepository: UserActionsRepository {

    private val dataBase = Firebase.firestore

    override suspend fun addApplicationToProject(
        currentUserId: String,
        projectId: String
    ): Boolean {
        try {
            Log.d("Debug","userid" + currentUserId + "projectId" + projectId)
            //val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
            if (!checkUserAppliedToProject(currentUserId, projectId)) {
                dataBase.collection("Users").document(currentUserId)
                    .update("projects_applications", FieldValue.arrayUnion(projectId))
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun checkUserAppliedToProject(currentUserId: String, projectId: String): Boolean{
        val userDocument = dataBase.collection("Users").document(currentUserId).get().await()
        val userApplications = userDocument.get("projects_applications") as? List<String> ?: emptyList()
        Log.d("FirebaseUserActionsRepository", "contains project - ${userApplications.contains(projectId)} ")
        return userApplications.contains(projectId)
    }

}