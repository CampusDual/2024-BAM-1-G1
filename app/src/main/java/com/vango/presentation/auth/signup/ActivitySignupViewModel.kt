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

    private val _errorEmail: MutableLiveData<Boolean> = MutableLiveData()
    val errorEmail: MutableLiveData<Boolean> = _errorEmail

    fun setEmail(email: String) {
        _email.value = email
        _errorEmail.value = !authUseCase.validEmail(email)
    }


}