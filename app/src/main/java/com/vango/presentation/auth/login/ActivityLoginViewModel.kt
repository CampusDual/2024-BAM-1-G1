package com.vango.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSuccess:LiveData<Boolean> = _isLoginSuccess

    private var email:String = ""
    private var password:String = ""

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading


    fun setEmail(text:String){
        email = text

    }

    fun setPassword(text:String){
        password = text

    }


    fun login(){
        if(email.isBlank() || password.isBlank())
        {
            _isLoading.value = false
            _error.value = "Por favor, complete todos los campos"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            authUseCase.logIn(email, password)
                .onSuccess { user ->
                    _success.postValue("Inicio de sesión exitoso")
                    _isLoginSuccess.postValue(true)
                }
                .onFailure { error ->
                    _error.postValue(error.localizedMessage)
                    _isLoginSuccess.postValue(false)
                }
            _isLoading.value = false
        }

    }
}