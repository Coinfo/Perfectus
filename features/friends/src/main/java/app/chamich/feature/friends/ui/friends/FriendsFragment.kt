/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.friends.ui.friends

import app.chamich.feature.friends.R
import app.chamich.feature.friends.databinding.FriendsFragmentFriendsBinding
import app.chamich.library.core.CoreFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class FriendsFragment :
    CoreFragment<FriendsViewModel, FriendsFragmentFriendsBinding>() {

    //---- region Fragment Overridden Functions

    override fun getLayoutId() = R.layout.friends_fragment_friends

    override fun onResume() {
        super.onResume()

        // Show options menu when fragment became visible
        setHasOptionsMenu(true)
    }

    override fun onPause() {
        super.onPause()

        // Hide options menu when fragment become invisible
        setHasOptionsMenu(false)
    }

    override fun getViewModelClass() = FriendsViewModel::class.java

    //---- endregion
}
