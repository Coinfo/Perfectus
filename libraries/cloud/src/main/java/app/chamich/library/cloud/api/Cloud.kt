/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.cloud.api

import app.chamich.library.cloud.exception.CloudException

/**
 * This interface provides cloud functionality.
 */
interface Cloud {

    /**
     * Creates profile in the cloud.
     *
     * @param profile The profile data.
     */
    @Throws(CloudException::class)
    suspend fun createCloudProfile(profile: Profile)
}
