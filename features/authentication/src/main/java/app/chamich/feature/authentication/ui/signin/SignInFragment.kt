/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AuthenticationFragmentSignInBinding
import app.chamich.feature.authentication.extenstion.hideKeyboardFrom
import app.chamich.feature.authentication.extenstion.textAsString
import app.chamich.feature.authentication.model.Destinations
import app.chamich.feature.authentication.model.Status
import app.chamich.feature.authentication.ui.BaseFragment
import app.chamich.library.authentication.IUser
import app.chamich.library.logger.ILogger
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Fragment responsible for User Sign In with Email and Password
 */
@AndroidEntryPoint
internal class SignInFragment :
    BaseFragment<SignInViewModel, AuthenticationFragmentSignInBinding>() {

    @Inject
    lateinit var logger: ILogger

    private var listener: SignInListener? = null
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

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

    override fun onDestroyView() {
        super.onDestroyView()

        // IMPROVEME: Fine a better way of resetting User State
        viewModel.resetSignedInUser()
    }

    override fun getViewModelClass() = SignInViewModel::class.java

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun onClickSignIn(view: View) {
        requireContext().hideKeyboardFrom(view)
        viewModel.signIn(
            binding.editTextSignInEmail.textAsString,
            binding.editTextSignInPassword.textAsString
        )
    }

    fun onClickForgotPassword() {
        findNavController().navigate(Destinations.ForgotPassword.direction)
    }

    fun onClickSignUp() {
        findNavController().navigate(Destinations.SignUp.direction)
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupBindings() {
        binding.fragment = this
        binding.buttonGoogleSignIn.setOnClickListener {
            activityResult.launch(viewModel.getGoogleSignInIntent(requireContext()))
        }
    }

    private fun setupSignedInUserObservable() {
        viewModel.getSignedInUser().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleStatusSuccess(result.data)
                Status.LOADING -> handleStatusLoading()
                Status.ERROR -> handleStatusError(result.exception)
            }
        })
    }

    private fun handleStatusSuccess(user: IUser?) {
        showProgress(false)

        user?.let { listener?.onSignInCompleted(it) }
    }

    private fun handleStatusLoading() {
        showProgress(true)
    }

    private fun handleStatusError(exception: Exception?) {
        showProgress(false)

        exception?.localizedMessage?.let { message ->
            // IMPROVEME: This snackbar can be an extension
            Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE).apply {
                // Change the default maximum number of text lines for the snackbar
                view.findViewById<TextView>(
                    com.google.android.material.R.id.snackbar_text
                ).maxLines = SNACKBAR_MAX_LINES
                // Set the snackbar action
                setAction(android.R.string.ok) {
                    // Closes the Snackbar
                }
            }.show()
        }
    }

    private fun showProgress(show: Boolean) {
        binding.progressSignIn.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding.linearLayoutSignInInput.visibility = if (show) View.INVISIBLE else View.VISIBLE
        binding.buttonSignUp.isEnabled = !show
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Companion Object

    companion object {
        private const val SNACKBAR_MAX_LINES = 5
    }

    //endregion
}
