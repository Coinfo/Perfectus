/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AuthenticationFragmentSignInBinding
import app.chamich.feature.authentication.ui.BaseFragment
import app.chamich.library.authentication.IUser
import app.chamich.library.core.model.Status
import app.chamich.library.snackbar.PerfectusSnackbar
import dagger.hilt.android.AndroidEntryPoint


/**
 * Fragment responsible for User Sign In with Email and Password
 */
@AndroidEntryPoint
internal class SignInFragment :
    BaseFragment<SignInViewModel, AuthenticationFragmentSignInBinding>() {

    private var listener: SignInListener? = null
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    //---- region Fragment Override Functions

    override fun getLayoutId() = R.layout.authentication_fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBindings()
        setupSignedInUserObservable()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activityResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                viewModel.handleGoogleSignIn(it.data)
            }

        listener = context as? SignInListener ?: throw IllegalStateException(
            "Your activity should implement SignInListener"
        )
    }

    override fun getViewModelClass() = SignInViewModel::class.java

    //---- endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////

    //---- region Private Functions

    private fun setupBindings() {
        binding.fragment = this
        binding.buttonGoogleSignIn.setOnClickListener {
            activityResult.launch(viewModel.getGoogleSignInIntent(requireContext()))
        }
    }

    private fun setupSignedInUserObservable() {
        viewModel.user.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleStatusSuccess(result.data)
                Status.LOADING -> handleStatusLoading()
                Status.FAILURE -> handleStatusError(result.exception)
            }
        })
    }

    private fun handleStatusSuccess(user: IUser?) {
        user?.let { listener?.onSignInCompleted(it) }
    }

    private fun handleStatusLoading() {
        showProgress(true)
    }

    private fun handleStatusError(exception: Exception?) {
        showProgress(false)
        exception?.localizedMessage?.let { message ->
            view?.let { PerfectusSnackbar.make(it, message).show() }
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressBarGoogle.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding.buttonGoogleSignIn.visibility = if (show) View.INVISIBLE else View.VISIBLE
    }

    //---- endregion
}
