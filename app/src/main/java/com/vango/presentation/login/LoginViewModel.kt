package com.vango.presentation.login

import androidx.lifecycle.ViewModel
import com.vango.data.dataSource.user.local.dbo.UserDbo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    fun insertUser(user: UserDbo) {

    }

}
