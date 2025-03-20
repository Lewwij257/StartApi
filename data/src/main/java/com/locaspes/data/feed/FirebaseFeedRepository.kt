package com.locaspes.data.feed

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseFeedRepository: FeedRepository {

    override fun getAllProjects(): Flow<List<ProjectCard>> = flow {
        val projectsDatabaseRef = FirebaseFirestore.getInstance().collection("Projects")
        try{
            val snapshot = projectsDatabaseRef.get().await()
            val projects = snapshot.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject(ProjectCard::class.java)?.copy(id = documentSnapshot.id)
            }
            emit(projects)
        }
        catch (e: Exception){
            throw e
        }
    }
}