package com.vango.presentation.onboarding.onboarding1

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.databinding.ActivityOnboardingOneBinding
import com.vango.presentation.onboarding.onboarding2.ActivityOnboardingTwo
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityOnboardingOne : AppCompatActivity() {
    var binding: ActivityOnboardingOneBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingOneBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val btnNext = binding?.ivArrowRight

        btnNext?.setOnClickListener{
            val intent = Intent(this, ActivityOnboardingTwo::class.java)
            startActivity(intent)
        }

    }
}