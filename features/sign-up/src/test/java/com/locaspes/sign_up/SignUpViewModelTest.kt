package com.locaspes.sign_up

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.locaspes.data.model.AuthResult
import com.locaspes.data.model.UserProfile
import com.locaspes.startapi.SignUpUiState
import com.locaspes.startapi.SignUpUseCase
import com.locaspes.startapi.SignUpViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock


@OptIn(ExperimentalCoroutinesApi::class)
class SignUpViewModelTest {
    private lateinit var signUpUseCase: SignUpUseCase
    private lateinit var viewModel: SignUpViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @get: Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        Dispatchers.setMain(testDispatcher)
        signUpUseCase = mockk<SignUpUseCase>()
        viewModel = SignUpViewModel(signUpUseCase)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `updateEmail should update email in uiState`(){
        val newEmail = "test@example.com"
        viewModel.updateEmail(newEmail)
        assertEquals(newEmail, viewModel.uiState.value.email)
    }

    @Test
    fun `updateUsername should update username`(){
        val newUsername = "Kirieshka"
        viewModel.updateUsername(newUsername)
        assertEquals(newUsername, viewModel.uiState.value.username)
    }

    @Test
    fun `updatePassword should update password`(){
        val newPassword = "abrakodabra228"
        viewModel.updatePassword(newPassword)
        assertEquals(newPassword, viewModel.uiState.value.password)
    }

//    @Test
//    fun `signUp should show loading and success after success registration`() = runTest{
//        val userProfile = UserProfile(
//            username =  "username",
//            email = "testemail@gmail.com",
//            password = "qwerty123")
//        coEvery { signUpUseCase.signUp(userProfile) } returns AuthResult.Success("123")
//        viewModel.updateEmail(userProfile.email)
//        viewModel.updateUsername(userProfile.username)
//        viewModel.updatePassword(userProfile.password)
//        viewModel.signUp()
//        assertTrue(viewModel.uiState.value is SignUpUiState.Loading)
//        assertTrue(viewModel.uiState.value is SignUpUiState.Success)
//        assertEquals("123", (viewModel.uiState.value as SignUpUiState.Success).userId)
//    }






}