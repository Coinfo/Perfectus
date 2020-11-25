/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.friends.ui.add

import app.chamich.feature.friends.R
import app.chamich.feature.friends.databinding.FriendsFragmentFriendsBinding
import app.chamich.feature.friends.ui.friends.FriendsViewModel
import app.chamich.library.core.CoreDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class AddFriendsFragment :
    CoreDialogFragment<FriendsViewModel, FriendsFragmentFriendsBinding>() {

    override fun getLayoutId() = R.layout.friends_dialog_fragment_add_friends

    override fun getViewModelClass() = FriendsViewModel::class.java
}
