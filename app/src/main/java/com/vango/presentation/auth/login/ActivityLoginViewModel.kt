package com.vango.presentation.auth.login

import android.util.Log
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
//  TODO: revisar esto cuando tengamos ActivityHome
//  TODO: revisar authUseCase.validEmail y .validPassword

    fun login(){
//        val isEmailValid = authUseCase.validEmail(email)
//        val isPasswordValid = authUseCase.validPassword(password)

        Log.d("AuthDebug", "Intentando login con email: $email y password: $password")


//        if (isEmailValid.first && isPasswordValid.first){
        viewModelScope.launch {
            val result = authUseCase.login(email, password)
            Log.d("AuthDebug", "Resultado del login: $result")
            _isLoginSuccess.value = result


        }
//        }

//        else {
//            Log.e("AuthDebug", "Email o password no válidos")
//
////            TODO: isLoginFormValid.value
//        }


    }
}