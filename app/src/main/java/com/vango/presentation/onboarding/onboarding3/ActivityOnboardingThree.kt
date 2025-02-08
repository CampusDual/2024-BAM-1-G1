package com.vango.presentation.onboarding.onboarding3

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.databinding.ActivityOnboardingThreeBinding
import com.vango.presentation.auth.accessAccount.ActivityAccessAccount
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityOnboardingThree : AppCompatActivity() {
    var binding: ActivityOnboardingThreeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnNext = binding?.btnOnboardingButton
        btnNext?.setOnClickListener{
            val intent = Intent(this, ActivityAccessAccount::class.java)
            startActivity(intent)
            // clear back stack
            finishAffinity()
        }

    }

}

