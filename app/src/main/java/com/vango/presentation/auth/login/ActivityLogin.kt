package com.vango.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

import com.vango.databinding.ActivityLoginBinding
import com.vango.presentation.auth.signup.ActivitySignup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null
    var viewModel: ActivityLoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val linkSignup = binding?.tvNoLoginRegister
        linkSignup?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
            finish()
        }

    }
}