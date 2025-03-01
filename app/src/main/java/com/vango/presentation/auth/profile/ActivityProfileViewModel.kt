package com.vango.presentation.auth.profile

import androidx.lifecycle.ViewModel

class ActivityProfileViewModel() : ViewModel() {

    private val buttonStates = BooleanArray(4) { false }

    fun toggleButtonState(index: Int) {
        buttonStates[index] = !buttonStates[index]
    }

    fun getButtonState(index: Int): Boolean {
        return buttonStates[index]
    }

}