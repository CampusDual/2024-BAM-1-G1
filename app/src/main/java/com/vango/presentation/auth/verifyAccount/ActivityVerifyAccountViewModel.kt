package com.vango.presentation.auth.verifyAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityVerifyAccountViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isAccountVerified: MutableLiveData<Boolean> = MutableLiveData()
    val isAccountVerified:LiveData<Boolean> = _isAccountVerified

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success

    private var code:String = ""
    private var codeOne:String = ""
    private var codeTwo:String = ""
    private var codeThree:String = ""
    private var codeFour:String = ""


    fun resendEmailVerification(){
        viewModelScope.launch {

        }
    }

    fun setTextOne(text:String){
        codeOne = text

    }
    fun setTextTwo(text:String){
        codeTwo = text

    }
    fun setTextThree(text:String){
        codeThree = text

    }
    fun setTextFour(text:String){
        codeFour = text

    }



    fun verifyCode() {

        code = "$codeOne$codeTwo$codeThree$codeFour"

        if(code.length == 4){
            viewModelScope.launch {
                val result = authUseCase.verifyUserEmail("firebaseId", code)
                if(result.isSuccess){
                    _success.postValue("Código de verificación correcto")
                    _isAccountVerified.postValue(true)
                }else{
                    _error.postValue("Código de verificación incorrecto")
                    _isAccountVerified.postValue(false)
                }
            }
        }
    }
}