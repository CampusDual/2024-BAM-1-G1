package com.vango.presentation.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vango.data.preferences.OnboardingPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel

class ActivityOnboardingViewModel @Inject constructor(): ViewModel(){
    private var _showOnBoarding = MutableLiveData<Boolean>()
    var showOnBoarding : LiveData<Boolean> = _showOnBoarding
    @Inject
    lateinit var onboardingPreferences: OnboardingPreferences
    init {
        viewModelScope.launch {
            _showOnBoarding.value = onboardingPreferences.isOnboardingCompleted()
        }
    }

    fun markOnboardingAsCompleted() {
        viewModelScope.launch {
            onboardingPreferences.setOnboardingCompleted(true)
        }
    }
}