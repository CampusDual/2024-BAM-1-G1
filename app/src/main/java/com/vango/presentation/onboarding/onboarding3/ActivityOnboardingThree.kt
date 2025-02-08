package com.vango.presentation.onboarding.onboarding3

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import com.vango.presentation.auth.accessAccount.ActivityAccessAccount

class ActivityOnboardingThree : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding_three)
        val btnNext = findViewById<Button>(R.id.btn_onboarding_button)

        btnNext.setOnClickListener{
            val intent = Intent(this, ActivityAccessAccount::class.java)
            startActivity(intent)
        }
    }
}

