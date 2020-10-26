/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.profile.ui.menu

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import app.chamich.library.logger.ILogger

internal class ProfileMenuViewModel @ViewModelInject constructor(
    private val logger: ILogger
) : ViewModel() {

    fun a() {
        logger.debug(message = "111")
    }

}
