/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.cloud

import app.chamich.library.cloud.api.Cloud
import app.chamich.library.cloud.api.Profile
import app.chamich.library.cloud.exception.CloudException
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class FirestoreCloud(
    private val database: FirebaseFirestore = Firebase.firestore,
) : Cloud {

    //---- region Cloud Functionality

    @Throws(CloudException::class)
    override suspend fun createCloudProfile(profile: Profile) {
        // This method will be called only once, after user signed in successful.
        try {
            database.collection(COLLECTION_ID_PROFILES).document(profile.id).set(profile).await()
        } catch (exception: FirebaseFirestoreException) {
            throw CloudException(exception.message, exception)
        }
    }

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- region Companion Object

    private companion object {

        const val COLLECTION_ID_PROFILES = "profiles"

    }

    //---- endregion
}
