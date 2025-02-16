package com.vango.presentation.auth.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vango.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ActivitySignupViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel() {
    private val _email: MutableLiveData<String> = MutableLiveData()
    val email: MutableLiveData<String> = _email

    private val _password: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = _password

    private val _confirmPassword: MutableLiveData<String> = MutableLiveData()
    val confirmPassword: MutableLiveData<String> = _confirmPassword

    private val _errorEmail: MutableLiveData<Boolean> = MutableLiveData()
    val errorEmail: MutableLiveData<Boolean> = _errorEmail

    private val _errorPassword: MutableLiveData<Boolean> = MutableLiveData()
    val errorPassword: MutableLiveData<Boolean> = _errorPassword

    private val _errorConfirmPassword: MutableLiveData<Boolean> = MutableLiveData()
    val errorConfirmPassword: MutableLiveData<Boolean> = _errorConfirmPassword


    fun setEmail(email: String) {
        _email.value = email
        _errorEmail.value = !authUseCase.validEmail(email)
    }

    fun setPassword(password: String) {
        _password.value = password
        _errorPassword.value = !authUseCase.validPassword(password)
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPassword.value = confirmPassword
        Log.d("ActivitySignupViewModel", "setConfirmPassword: $confirmPassword")
        _errorConfirmPassword.value = _password.value?.let { authUseCase.validConfirmPassword(it, confirmPassword) }
        Log.d("ActivitySignupViewModel", "setConfirmPassword: ${_errorConfirmPassword.value}")
    }




}