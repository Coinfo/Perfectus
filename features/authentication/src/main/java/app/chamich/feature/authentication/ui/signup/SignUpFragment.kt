/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.signup

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AuthenticationFragmentSignUpBinding
import app.chamich.feature.authentication.extenstion.hideKeyboardFrom
import app.chamich.feature.authentication.extenstion.textAsString
import app.chamich.feature.authentication.model.Status
import app.chamich.feature.authentication.ui.BaseFragment
import app.chamich.library.authentication.IUser
import app.chamich.library.logger.ILogger
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
internal class SignUpFragment :
    BaseFragment<SignUpViewModel, AuthenticationFragmentSignUpBinding>() {

    @Inject
    lateinit var logger: ILogger

    private var listener: SignUpListener? = null

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.authentication_fragment_sign_up

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        setupSignedInUserObservable()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context !is SignUpListener) {
            logger.warn(
                message = "In order to listen for Sign Up Completion event," +
                        " your activity should implement SignUpFragment.SignUpListener"
            )
        }

        listener = context as? SignUpListener
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // IMPROVEME: Fine a better way of resetting User State
        viewModel.resetSignedUpUser()
    }

    override fun getViewModelClass() = SignUpViewModel::class.java

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun onClickSignUp(view: View) {
        requireContext().hideKeyboardFrom(view)
        viewModel.signUp(
            binding.editTextSignUpEmail.textAsString,
            binding.editTextSignUpPassword.textAsString
        )
    }

    fun onClickSignIn() {
        findNavController().popBackStack()
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupSignedInUserObservable() {
        viewModel.getSignedUpUser().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleStatusSuccess(result.data)
                Status.LOADING -> handleStatusLoading()
                Status.ERROR -> handleStatusError(result.exception)
            }
        })
    }

    private fun handleStatusSuccess(user: IUser?) {
        showProgress(false)

        user?.let { listener?.onSignUpCompleted(it) }
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
        binding.progressSignUp.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding.linearLayoutSignUpInput.visibility = if (show) View.INVISIBLE else View.VISIBLE
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