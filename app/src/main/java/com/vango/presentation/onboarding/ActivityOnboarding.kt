package com.vango.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.vango.data.preferences.OnboardingPreferences
import com.vango.databinding.ActivityOnboardingBinding
import com.vango.databinding.FragmentOnboardingBinding
import com.vango.presentation.auth.accessAccount.ActivityAccessAccount
import com.vango.presentation.base.BaseActivity
import com.vango.presentation.main.ActivityMain
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityOnboarding : BaseActivity() {
    private var binding: ActivityOnboardingBinding? = null
    private val viewModel: ActivityOnboardingViewModel by viewModels()
    private var oldView: View? = null

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
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding?.root)
//        val btnNext = binding?.ivArrowRight

//        btnNext?.setOnClickListener{
//            val intent = Intent(this, ActivityOnboardingTwo::class.java)
//            startActivity(intent)
//        }

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