package app.chamich.feature.authentication.ui.signup

import app.chamich.feature.authentication.R
import app.chamich.feature.authentication.databinding.AccountFragmentSignUpBinding
import app.chamich.feature.authentication.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
internal class SignUpFragment : BaseFragment<SignUpViewModel, AccountFragmentSignUpBinding>() {


    override fun getViewModelClass(): Class<SignUpViewModel> = SignUpViewModel::class.java
    override fun getLayoutId() = R.layout.account_fragment_sign_up

}
