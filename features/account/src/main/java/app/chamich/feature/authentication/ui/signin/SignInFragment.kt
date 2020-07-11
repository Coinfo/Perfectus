package app.chamich.feature.authentication.ui.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.chamich.feature.authentication.R
import app.chamich.library.logger.ILogger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : Fragment() {

    @Inject
    lateinit var logger: ILogger

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_sign_in, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logger.debug(message = "Sign In View Created")
        button_login.setOnClickListener {
            logger.debug(message = "Sign In [email: ${edittext_email.text} " +
                    "password: ${edittext_password.text}]")
        }
    }
}
