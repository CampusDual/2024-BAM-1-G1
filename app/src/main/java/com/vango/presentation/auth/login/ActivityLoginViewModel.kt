package com.vango.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityLoginViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isLoginSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isLoginSuccess:LiveData<Boolean> = _isLoginSuccess

    private var _canDoLogin: MutableLiveData<Boolean> = MutableLiveData()
    val canDoLogin:LiveData<Boolean> = _canDoLogin
    // TODO: hacer validaciones

    private var email:String = ""
    private var password:String = ""

    fun setEmail(text:String){
        email = text
    }

    fun setPassword(text:String){
        password = text
    }

    fun login(){
        val isEmailValid = authUseCase.validEmail(email)
        val isPasswordValid = authUseCase.validPassword(password)
        if (isEmailValid.first && isPasswordValid.first){
            viewModelScope.launch {
                _isLoginSuccess.value = authUseCase.login(email,password)
            }
        }

        else {
//            TODO: isLoginFormValid.value
        }


    }
}