/*
 * Copyright (c) 2020 Chamich Apps. All rights reserved.
 */

package app.chamich.feature.authentication.ui.password.forgot

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AuthenticationFragmentForgotPasswordBinding
import app.chamich.feature.authentication.extenstion.hideKeyboardFrom
import app.chamich.feature.authentication.extenstion.textAsString
import app.chamich.feature.authentication.model.Status
import app.chamich.feature.authentication.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class ForgotPasswordFragment :
    BaseFragment<ForgotPasswordViewModel, AuthenticationFragmentForgotPasswordBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.authentication_fragment_forgot_password

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        setupSignedInUserObservable()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // IMPROVEME: Fine a better way of resetting User State
        viewModel.resetResetPassword()
    }

    override fun getViewModelClass() = ForgotPasswordViewModel::class.java

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun onClickResetPassword(view: View) {
        requireContext().hideKeyboardFrom(view)
        viewModel.resetPassword(binding.editTextForgotPasswordEmail.textAsString)
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

    private fun setupSignedInUserObservable() {
        viewModel.getResetPassword().observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Status.SUCCESS -> handleStatusSuccess(result.data)
                Status.LOADING -> handleStatusLoading()
                Status.ERROR -> handleStatusError(result.exception)
            }
        })
    }

    private fun handleStatusSuccess(ignore: Unit?) {
        showProgress(false)

        findNavController().popBackStack()
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
        binding.progressForgetPassword.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding.linearLayoutForgotPasswordInput.visibility =
            if (show) View.INVISIBLE else View.VISIBLE
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Companion Object

    companion object {
        private const val SNACKBAR_MAX_LINES = 5
    }

    //endregion

    //endregion
}
