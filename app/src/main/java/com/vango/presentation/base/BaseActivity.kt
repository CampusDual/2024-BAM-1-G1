package com.vango.presentation.base

import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback

open class BaseActivity : ComponentActivity() {
    private var backPressedTime: Long = 0
    private val backPressedInterval: Long = 2000

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTaskToBack(true)
            }
        })
    }


}