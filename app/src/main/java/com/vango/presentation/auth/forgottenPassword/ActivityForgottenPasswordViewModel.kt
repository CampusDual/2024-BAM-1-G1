package com.vango.presentation.auth.forgottenPassword


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.domain.usecase.auth.AuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityForgottenPasswordViewModel @Inject constructor(private val authUseCase: AuthUseCase) : ViewModel()  {
    private var _isResetPasswordSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val isResetPasswordSuccess:LiveData<Boolean> = _isResetPasswordSuccess

    private var email:String = ""

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _success: MutableLiveData<String> = MutableLiveData()
    val success: LiveData<String> = _success

    fun setEmail(text:String){
        email = text

    }


    fun resetPassword(){

        viewModelScope.launch {

            authUseCase.recoverPassword(email)
                .onSuccess {
                    _isResetPasswordSuccess.postValue(it)
                }
                .onFailure { error ->
                    _error.postValue(error.localizedMessage)
                }
        }
    }
}