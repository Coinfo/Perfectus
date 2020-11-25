/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.friends.ui.add

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.ViewGroup
import app.chamich.feature.friends.R
import app.chamich.feature.friends.databinding.FriendsDialogFragmentAddFriendsBinding
import app.chamich.library.cloud.api.Profile
import app.chamich.library.core.CoreDialogFragment
import app.chamich.library.core.extensions.asTrimmedString
import app.chamich.library.core.model.Status
import app.chamich.library.snackbar.PerfectusSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
internal class AddFriendsFragment :
    CoreDialogFragment<AddFriendsViewModel, FriendsDialogFragmentAddFriendsBinding>() {

    //---- region Fragment Overridden Functions

    override fun getLayoutId() = R.layout.friends_dialog_fragment_add_friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.Perfectus_FullScreenDialog)
    }

    override fun onStart() {
        super.onStart()

        dialog?.let { dialog ->
            dialog.window?.let { window ->
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                window.setWindowAnimations(R.style.Slide)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupObservers()
    }

    override fun getViewModelClass() = AddFriendsViewModel::class.java

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- region Binding Functions

    fun onSearchClicked() {
        val email = binding.editTextFriendEmail.asTrimmedString

        // Validate user input for being correct email address
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            view?.let { it ->
                PerfectusSnackbar.make(
                    it, R.string.friends_add_friends_shackbar_text_invalid_email
                ).show()
            }
        } else {
            viewModel.searchProfile(email)
        }
    }

    fun onAddClicked() {
        //
    }

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- Private Functions

    private fun setupBindings() {
        binding.fragment = this
    }

    private fun setupObservers() {
        viewModel.foundProfile.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> handleSearchSuccess(result.data)
                Status.LOADING -> handleLoading()
                Status.FAILURE -> handleFailure(result.exception)
            }
        }
    }

    private fun handleSearchSuccess(profile: Profile?) {
        profile?.let {
            binding.textViewName.text = it.name
            binding.textViewEmail.text = it.email
        }
    }

    private fun handleLoading() {
        //
    }

    private fun handleFailure(exception: Exception?) {
        //
    }

    //---- endregion
}
