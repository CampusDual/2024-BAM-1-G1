package com.vango.presentation.onboarding.onboarding1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import com.google.firebase.auth.FirebaseAuth
import com.vango.data.preferences.OnboardingPreferences
import com.vango.databinding.ActivityOnboardingOneBinding
import com.vango.presentation.auth.accessAccount.ActivityAccessAccount
import com.vango.presentation.base.BaseActivity
import com.vango.presentation.main.ActivityMain
import com.vango.presentation.onboarding.onboarding2.ActivityOnboardingTwo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityOnboardingOne : BaseActivity() {
    private var binding: ActivityOnboardingOneBinding? = null
    @Inject
    lateinit var onboardingPreferences: OnboardingPreferences
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isFirstLaunch(this)) {
            FirebaseAuth.getInstance().apply {
                signOut()
            }
        }


        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this, ActivityMain::class.java)
            startActivity(intent)
            finish()
            return
        }
        if (onboardingPreferences.isOnboardingCompleted()) {

            val intent = Intent(this, ActivityAccessAccount::class.java)
            startActivity(intent)
            finish()
            return
        }

        enableEdgeToEdge()
        binding = ActivityOnboardingOneBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        val btnNext = binding?.ivArrowRight

        btnNext?.setOnClickListener{
            val intent = Intent(this, ActivityOnboardingTwo::class.java)
            startActivity(intent)
        }

    }
    private fun isFirstLaunch(context: Context): Boolean {
        val prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        val isFirstLaunch = prefs.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            prefs.edit().putBoolean("isFirstLaunch", false).apply()
            return true
        }

        return false
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}