package com.vango.presentation.onboarding.onboarding2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import com.vango.presentation.onboarding.onboarding3.ActivityOnboardingThree

class ActivityOnboardingTwo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_two)

        val btnNext = findViewById<ImageView>(R.id.iv_arrow_right)

        btnNext.setOnClickListener{
            val intent = Intent(this, ActivityOnboardingThree::class.java)
            startActivity(intent)
        }

    }

}