package com.locaspes.sign_up

import com.locaspes.startapi.SignUpUseCase
import com.locaspes.startapi.SignUpViewModel


@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var viewModel: SignUpViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun 'updateEmail should udpate email in UiState'(){
        val viewModel = SignUpViewModel(signUpUseCase = null)
        val newEmail = "test@example.com"
        viewModel.updateEmail(newEmail)
        val state = viewModel.uiState.value
        assertEquals(newEmail, state.email)
    }
}