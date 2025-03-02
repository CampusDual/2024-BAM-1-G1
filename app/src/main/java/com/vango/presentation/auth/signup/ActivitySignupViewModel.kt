package com.vango.presentation.auth.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.auth.AuthUseCase
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

    private val _isSignUpSuccessful: MutableLiveData<Boolean> = MutableLiveData()
    val isSignUpSuccessful: LiveData<Boolean> = _isSignUpSuccessful

    private var _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


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
                _isLoading.value = true
                val result = authUseCase.signUp(emailValue, passwordValue, 1)
                if (result.isSuccess) {
                    _isSignUpSuccessful.value = true
                } else if (result.isFailure) {
                    _error.value = result.exceptionOrNull()?.message
                    _isSignUpSuccessful.value = false
                }
                _isLoading.value = false
            }
        }
    }








}