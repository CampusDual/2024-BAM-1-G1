package com.vango.presentation.auth.accessAccount

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import com.vango.databinding.ActivityAccessAccountBinding
import com.vango.presentation.onboarding.onboarding3.ActivityOnboardingThree
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAccessAccount : AppCompatActivity() {
    private var binding: ActivityAccessAccountBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_access_account)

    }
}