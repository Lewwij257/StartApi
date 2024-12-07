package com.locaspes.accounts.sources

import com.google.android.gms.tasks.Task

interface FireStoreDataFetcher {

    fun getCollection(collection: String): MutableList<Map<String, Any>>

}