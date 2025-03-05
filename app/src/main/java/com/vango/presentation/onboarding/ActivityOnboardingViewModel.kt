package com.vango.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class ActivityOnboardingViewModel @Inject constructor(): ViewModel(){
    private var _showOnBoarding = MutableLiveData<Boolean>()
    var showOnBoarding : LiveData<Boolean> = _showOnBoarding

}