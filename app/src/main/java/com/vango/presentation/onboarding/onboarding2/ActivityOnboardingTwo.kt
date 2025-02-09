package com.vango.presentation.onboarding.onboarding2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import com.vango.databinding.ActivityOnboardingTwoBinding
import com.vango.presentation.onboarding.onboarding3.ActivityOnboardingThree
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityOnboardingTwo : AppCompatActivity() {
    var binding: ActivityOnboardingTwoBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnboardingTwoBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnNext = binding?.ivArrowRight

        btnNext?.setOnClickListener{
            val intent = Intent(this, ActivityOnboardingThree::class.java)
            startActivity(intent)
        }

    }

}