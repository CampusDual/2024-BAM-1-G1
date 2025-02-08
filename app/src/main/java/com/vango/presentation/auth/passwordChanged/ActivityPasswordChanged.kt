package com.vango.presentation.auth.passwordChanged

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vango.R
import com.vango.databinding.ActivityPasswordChangedBinding
import com.vango.presentation.auth.login.ActivityLogin
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.log

@AndroidEntryPoint
class ActivityPasswordChanged : AppCompatActivity() {
    var binding: ActivityPasswordChangedBinding? = null
    var viewModel: ActivityPasswordChangedViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPasswordChangedBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnNext = binding?.tvPasswordChangedPassword

        btnNext?.setOnClickListener{
            // log cat btn click
            Log.d("ChangePassword", "btnNext click")

        }
    }
}