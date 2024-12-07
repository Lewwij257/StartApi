package com.locaspes.presentation.signup

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.locaspes.sign_up.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val username = binding.etUsernameInput
        val email = binding.etEmailInput
        val password = binding.etPasswordInput
        val passwordRepeat = binding.etPasswordRepeatInput
        val error = binding.tvErrorMessage

        val correctUsernameStateObserver = Observer<Boolean> { correct ->
            if (correct){
                username.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
            }
            else {
                username.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)
            }
        }

        val correctEmailStateObserver = Observer<Boolean> { correct ->
            if (correct){
                email.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
            }
            else {
                email.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)
            }
        }

        val correctPasswordStateObserver = Observer<Boolean> { correct ->
            if (correct){
                password.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
                //passwordRepeat.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
            }
            else {
                password.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)
                //passwordRepeat.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)
            }
        }

        val passwordsEqualsStateObserver = Observer<Boolean> {equals ->
            if (equals){
                //password.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
                passwordRepeat.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_correct_input)
            }
            else {
                //password.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)
                passwordRepeat.setBackgroundResource(com.locaspes.theme.R.drawable.et_bkg_default)

            }
        }

        val registerButtonAvailabilityObserver = Observer<Boolean> { aviable ->
            if (aviable){
                binding.btnSignUp.setBackgroundResource(com.locaspes.theme.R.drawable.btn_bkg_default)
                binding.btnSignUp.setTextColor(resources.getColor(com.locaspes.theme.R.color.white))
            }
            else{
                binding.btnSignUp.setBackgroundResource(com.locaspes.theme.R.drawable.btn_bkg_inactive)
                binding.btnSignUp.setTextColor(resources.getColor(com.locaspes.theme.R.color.dull_white))
            }
        }
        binding.btnSignUp.setOnClickListener {
            viewModel.signUp(username.text.toString(), email.text.toString(), password.text.toString())
        }

        username.doOnTextChanged{ _, _, _, _ ->
            viewModel.checkUsernameInput(username.text.toString())
            viewModel.checkSignUpAvailability()
        }

        email.doOnTextChanged{_,_,_,_ ->
            viewModel.checkEmailInput(email.text.toString())
            viewModel.checkSignUpAvailability()
        }

        password.doOnTextChanged{_,_,_,_ ->
            viewModel.checkPasswordInput(password.text.toString(), passwordRepeat.text.toString())
            viewModel.checkSignUpAvailability()
        }
        passwordRepeat.doOnTextChanged{_,_,_,_ ->
            viewModel.checkPasswordInput(password.text.toString(), passwordRepeat.text.toString())
            viewModel.checkSignUpAvailability()
        }

        viewModel.correctEmailState.observe(viewLifecycleOwner, correctEmailStateObserver)
        viewModel.correctUsernameState.observe(viewLifecycleOwner, correctUsernameStateObserver)
        viewModel.correctPasswordsState.observe(viewLifecycleOwner, correctPasswordStateObserver)
        viewModel.passwordEqualsState.observe(viewLifecycleOwner, passwordsEqualsStateObserver)
        viewModel.signUpAvailabilityState.observe(viewLifecycleOwner, registerButtonAvailabilityObserver)



    }
}