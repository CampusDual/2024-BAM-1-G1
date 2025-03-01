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

    fun setEmail(text:String){
        email = text

    }

    fun setPassword(text:String){
        password = text

    }


    fun login(){

        viewModelScope.launch {

            authUseCase.logIn(email, password)
                .onSuccess { user ->
                    _success.postValue("Inicio de sesión exitoso")
                }
                .onFailure { error ->
                    _error.postValue(error.localizedMessage)
                }
        }
    }
}