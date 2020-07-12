package app.chamich.feature.authentication.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import app.chamich.feature.authentication.R
import app.chamich.library.logger.ILogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.account_fragment_sign_in.*
import javax.inject.Inject


@AndroidEntryPoint
internal class SignInFragment : Fragment() {

    @Inject
    lateinit var logger: ILogger

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.account_fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.debug(message = "Sign In View Created")
        button_login.setOnClickListener {
            logger.debug(
                message = "Sign In [email: ${edittext_email.text} " +
                        "password: ${edittext_password.text}]"
            )
        }

        button_restore_password.setOnClickListener {
            logger.debug(message = "Forgot Password Clicked")
        }

        button_register.setOnClickListener {
            findNavController().navigate(R.id.destination_fragment_sign_up)
            logger.debug(message = "Register Clicked")
        }
    }
}
