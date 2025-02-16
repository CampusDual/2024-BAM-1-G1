package com.vango.presentation.auth.signup

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

    private val _errorEmail: MutableLiveData<Boolean> = MutableLiveData()
    val errorEmail: MutableLiveData<Boolean> = _errorEmail

    private val _errorPassword: MutableLiveData<Boolean> = MutableLiveData()
    val errorPassword: MutableLiveData<Boolean> = _errorPassword


    fun setEmail(email: String) {
        _email.value = email
        _errorEmail.value = !authUseCase.validEmail(email)
    }

    fun setPassword(password: String) {
        _password.value = password
        _errorPassword.value = !authUseCase.validPassword(password)
    }



}