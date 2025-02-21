package com.vango.presentation.auth.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.entities.AppError
import com.vango.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSuccess:LiveData<Boolean> = _isLoginSuccess

    private var email:String = ""
    private var password:String = ""

    private var _error: MutableLiveData<AppError> = MutableLiveData()
    val error: LiveData<AppError> = _error

    fun setEmail(text:String){
        email = text

    }

    fun setPassword(text:String){
        password = text

    }


    fun login(){

        viewModelScope.launch {
            try {
                val result = authUseCase.login(email, password)
                Log.d("LoginViewModel", "Login result: $result, email: $email, password: $password")
                _isLoginSuccess.value = result
            }
            catch (e: AppError){
                _error.value = e
                _isLoginSuccess.value = false
            }
        }
    }
}