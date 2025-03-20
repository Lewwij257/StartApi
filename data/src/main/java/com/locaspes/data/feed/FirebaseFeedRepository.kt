package com.locaspes.data.feed

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.locaspes.data.model.ProjectCard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class FirebaseFeedRepository: FeedRepository {

    val querySize = 10

    private val projectsDatabaseRef = FirebaseFirestore.getInstance().collection("Projects")

    override fun getAllProjects(): Flow<List<ProjectCard>> = flow {
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

    override fun getProjectsPaginated(lastDocument: DocumentSnapshot?): Flow<List<ProjectCard>> = flow {
        try {
            val query = if (lastDocument == null){
                projectsDatabaseRef.orderBy("createDate").limit(querySize.toLong())
            }
            else{
                projectsDatabaseRef.orderBy("createDate").startAfter(lastDocument).limit(querySize.toLong())
            }
            Log.d("FirebaseFeedRepository", "Executing query, lastDocument: $lastDocument")
            val snapshot = query.get().await()
            Log.d("FirebaseFeedRepository", "Query completed, documents received: ${snapshot.documents.size}")
            val projects = snapshot.documents.mapNotNull { documentSnapshot ->
                try {
                    val project = documentSnapshot.toObject(ProjectCard::class.java)
                    Log.d("FirebaseFeedRepository", "Raw project for ${documentSnapshot.id}: $project")
                    project?.copy(id = documentSnapshot.id).also {
                        Log.d("FirebaseFeedRepository", "Converted project for ${documentSnapshot.id}: $it")
                    }
                } catch (e: Exception) {
                    Log.e("FirebaseFeedRepository", "Error converting document ${documentSnapshot.id}: $e")
                    null
                }
            }
            Log.d("FirebaseFeedRepository", "Total projects after conversion: ${projects.size}")

            emit(projects)
        }
        catch (e: Exception){
            throw e
        }
    }

    override fun getLastDocument(
        projects: List<ProjectCard>,
        documents: List<DocumentSnapshot>
    ): DocumentSnapshot? {
        if (projects.isEmpty()){
            return null
        }
        val lastProjectIndex = projects.size-1
        return documents.getOrNull(lastProjectIndex)
    }

    suspend fun getDocuments(lastDocument: DocumentSnapshot?): List<DocumentSnapshot> {
        val query = if (lastDocument == null) {
            projectsDatabaseRef.orderBy("createDate").limit(querySize.toLong())
        } else {
            projectsDatabaseRef.orderBy("createDate").startAfter(lastDocument).limit(querySize.toLong())
        }
        return query.get().await().documents
    }

}