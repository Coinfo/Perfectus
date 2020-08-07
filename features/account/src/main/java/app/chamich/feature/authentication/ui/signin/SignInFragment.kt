package app.chamich.feature.authentication.ui.signin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AccountFragmentSignInBinding
import app.chamich.feature.authentication.extenstion.hideKeyboardFrom
import app.chamich.feature.authentication.extenstion.textAsString
import app.chamich.feature.authentication.model.Destinations
import app.chamich.feature.authentication.model.Status
import app.chamich.feature.authentication.ui.BaseFragment
import app.chamich.library.authentication.IUser
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment responsible for User Sign In with Email and Password
 */
@AndroidEntryPoint
internal class SignInFragment : BaseFragment<SignInViewModel, AccountFragmentSignInBinding>() {

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Fragment Override Functions

    override fun getLayoutId() = R.layout.account_fragment_sign_in

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this

        setupSignedInUserObservable()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // IMPROVEME: Fine a better way of resetting User State
        viewModel.resetSignedInUser()
    }

    override fun getViewModelClass(): Class<SignInViewModel> = SignInViewModel::class.java

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Public Functions

    fun onClickSignIn(view: View) {
        requireContext().hideKeyboardFrom(view)
        viewModel.signIn(
            binding.edittextEmail.textAsString,
            binding.edittextPassword.textAsString
        )
    }

    fun onClickForgotPassword() {
        //
    }

    fun onClickSignUp() {
        findNavController().navigate(Destinations.SignUp.direction)
    }

    //endregion

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //region Private Functions

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
        binding.progress.visibility = if (show) View.VISIBLE else View.INVISIBLE
        binding.linearLayoutUserInput.visibility = if (show) View.INVISIBLE else View.VISIBLE
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
