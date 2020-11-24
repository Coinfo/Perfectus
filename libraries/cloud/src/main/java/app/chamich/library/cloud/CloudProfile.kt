/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.library.cloud

import app.chamich.library.cloud.api.Profile

data class CloudProfile(
    override val id: String,
    override val email: String,
    override val name: String?,
    override val photo: String?,
) : Profile
