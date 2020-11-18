/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.goals.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import app.chamich.library.authentication.IAuthenticator

internal class GoalsPagerViewModel @ViewModelInject constructor(
    private val authenticator: IAuthenticator
) : ViewModel() {

    fun getDisplayedName(): String {
        val displayName = authenticator.getCurrentUser()?.displayName
        val splitDisplayName = displayName?.split(" ") ?: emptyList()
        if (splitDisplayName.isNotEmpty()) {
            return "Hi, ${splitDisplayName[0]}"
        }
        return "Hi, $displayName"
    }

}
