package com.locaspes.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.locaspes.accounts.entities.AccountDataEntity
import com.locaspes.accounts.sources.FireStoreDataFetcher
import javax.inject.Inject

class FireStoreDataFetcher @Inject constructor(): FireStoreDataFetcher {

    override fun getCollection(collection: String): MutableList<Map<String, Any>> {

        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("yourCollection")
        val localCollection = mutableListOf<Map<String, Any>>()

        collectionRef
            .get()
            .addOnSuccessListener{ querySnapshot ->
                querySnapshot.documents.forEach{document ->
                    localCollection.add(document.data!!)
                }
            }
        return localCollection
    }

}