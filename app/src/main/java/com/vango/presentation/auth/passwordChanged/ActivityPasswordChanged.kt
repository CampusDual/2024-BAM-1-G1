package com.vango.presentation.auth.passwordChanged

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.databinding.ActivityPasswordChangedBinding
import dagger.hilt.android.AndroidEntryPoint

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