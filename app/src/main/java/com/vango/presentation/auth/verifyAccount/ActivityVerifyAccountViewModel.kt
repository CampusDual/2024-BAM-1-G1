package com.vango.presentation.auth.verifyAccount

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@HiltViewModel
class ActivityVerifyAccountViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isAccountVerified: MutableLiveData<Boolean> = MutableLiveData()
    val isAccountVerified:LiveData<Boolean> = _isAccountVerified

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _resendTimer: MutableLiveData<Int> = MutableLiveData(-1)
    val resendTimer: LiveData<Int> = _resendTimer

    private val _isResendEnabled: MutableLiveData<Boolean> = MutableLiveData(true)
    val isResendEnabled: LiveData<Boolean> = _isResendEnabled

    private var code:String = ""
    private var codeOne:String = ""
    private var codeTwo:String = ""
    private var codeThree:String = ""
    private var codeFour:String = ""
    private var countdownJob: Job? = null
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

    init {
        startResendCountdown()
    }

    fun resendEmailVerification() {
        if (_isResendEnabled.value == false) return

        viewModelScope.launch {
            _success.postValue("Código reenviado")
            startResendCountdown()
        }
    }

    private fun startResendCountdown() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch {
            _isResendEnabled.value = false
            for (i in 40 downTo 0) {
                _resendTimer.postValue(i)
                delay(1000)
            }
            _isResendEnabled.value = true
            _resendTimer.postValue(-1)
        }
    }

    fun verifyCode() {
        code = "$codeOne$codeTwo$codeThree$codeFour"

        if(code.length == 4){
            viewModelScope.launch {
                _isLoading.value = true
                val result = authUseCase.verifyUserEmail("firebaseId", code)
                if(result.isSuccess){
                    _success.postValue("Código de verificación correcto")
                    _isAccountVerified.postValue(true)
                }else{
                    _error.postValue("Código de verificación incorrecto")
                    _isAccountVerified.postValue(false)
                }
                _isLoading.value = false
            }
        }
        startResendCountdown()
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}