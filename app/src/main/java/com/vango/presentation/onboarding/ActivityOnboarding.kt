package com.vango.presentation.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.vango.R
import com.vango.data.preferences.OnboardingPreferences
import com.vango.databinding.ActivityOnboardingBinding
import com.vango.presentation.auth.accessAccount.ActivityAccessAccount
import com.vango.presentation.main.ActivityMain
import com.vango.utils.animation.Animations
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityOnboarding : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
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

        updateTitleAndDescription(0)
        setOnboarding()

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

    private fun setOnboarding() {
        val onboardingAdapter = AdapterOnboarding(this)

        binding.viewPager.adapter = onboardingAdapter
        updateIndicators(0)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateIndicators(position)
                updateTitleAndDescription(position)

                if (position == onboardingAdapter.itemCount - 1) {
                    viewModel.markOnboardingAsCompleted()
                }
            }
        })

        binding.btnClose.setOnClickListener {
            onboardingPreferences.setOnboardingCompleted(true)
            val intent = Intent(this, ActivityAccessAccount::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnNext.setOnClickListener {

            if (binding.viewPager.currentItem < onboardingAdapter.itemCount - 1) {
                binding.viewPager.currentItem += 1
            }else {
                onboardingPreferences.setOnboardingCompleted(true)
                val intent = Intent(this, ActivityAccessAccount::class.java)
                startActivity(intent)
                finish()
            }
        }


    }

    private fun updateIndicators(position: Int) {
        binding.indicator1.isSelected = position == 0
        binding.indicator2.isSelected = position == 1
        binding.indicator3.isSelected = position == 2

        oldView?.let {
            Animations.animateWidthChange(it,30, 15,400)
        }

        oldView = when (position) {
            0 -> binding.indicator1
            1 -> binding.indicator2
            2 -> binding.indicator3
            else -> null
        }

        oldView?.let {
            Animations.animateWidthChange(it,30, 15,400)
        }
    }

    private fun updateTitleAndDescription(position: Int){
        when(position){
            0 -> {
                binding.tvOnboardingTitle.text = getString(R.string.onboarding_one_title)
                binding.tvOnboardingBody.text = getString(R.string.onboarding_one_body)
            }

            1 -> {
                binding.tvOnboardingTitle.text = getString(R.string.onboarding_two_title)
                binding.tvOnboardingBody.text = getString(R.string.onboarding_two_body)
            }

            2 -> {
                binding.tvOnboardingTitle.text = getString(R.string.onboarding_three_title)
                binding.tvOnboardingBody.text = getString(R.string.onboarding_three_body)
            }
        }
    }
}