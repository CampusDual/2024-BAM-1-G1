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


    fun resendEmailVerification(){
        viewModelScope.launch {

        }
    }
}