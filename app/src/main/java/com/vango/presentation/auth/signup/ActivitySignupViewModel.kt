package com.vango.presentation.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.AuthUseCase
import com.vango.domain.utils.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivitySignupViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = _email

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = _password

    private val _confirmPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = _confirmPassword

    private val _errorEmail: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    val errorEmail: MutableLiveData<Pair<Boolean, String>> = _errorEmail

    private val _errorPassword: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    val errorPassword: MutableLiveData<Pair<Boolean, String>> = _errorPassword

    private val _errorConfirmPassword: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    val errorConfirmPassword: MutableLiveData<Pair<Boolean, String>> = _errorConfirmPassword



    fun setEmail(email: String) {
        _email.value = email
        val (hasError, errorMessage) = authUseCase.validEmail(email)
        _errorEmail.value = Pair(!hasError, errorMessage)
    }

    fun setPassword(password: String) {
        _password.value = password
        val (hasError, errorMessage) = authUseCase.validPassword(password)
        _errorPassword.value = Pair(!hasError, errorMessage)
    }


    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        Log.d("ActivitySignupViewModel", "setConfirmPassword: $confirmPassword")

        val passwordValue = _password.value
        if (passwordValue != null) {
            val (hasError, errorMessage) = authUseCase.validConfirmPassword(passwordValue, confirmPassword)
            _errorConfirmPassword.value = Pair(!hasError, errorMessage)
        }

        Log.d("ActivitySignupViewModel", "setConfirmPassword: ${_errorConfirmPassword.value}")
    }

    fun signUp() {
        val emailValue = _email.value
        val passwordValue = _password.value
        val confirmPasswordValue = _confirmPassword.value
        if (emailValue != null && passwordValue != null && confirmPasswordValue != null) {
            viewModelScope.launch {
                val result = authUseCase.signUp(emailValue, passwordValue, confirmPasswordValue)

            }
        }

    }








}