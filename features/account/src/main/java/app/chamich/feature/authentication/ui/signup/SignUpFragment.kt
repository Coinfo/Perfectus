package app.chamich.feature.authentication.ui.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import app.chamich.feature.authentication.R

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class SignUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.account_fragment_sign_up, container, false)

}
