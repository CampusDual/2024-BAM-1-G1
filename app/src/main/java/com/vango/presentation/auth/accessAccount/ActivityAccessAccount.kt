package com.vango.presentation.auth.accessAccount

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.databinding.ActivityAccessAccountBinding
import com.vango.presentation.auth.login.ActivityLogin
import com.vango.presentation.auth.signup.ActivitySignup
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAccessAccount : AppCompatActivity() {
    private var binding: ActivityAccessAccountBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccessAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnLogin = binding?.btAccessAccountSignup
        btnLogin?.setOnClickListener{
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }

        val btnRegister = binding?.btAccessAccountLogin
        btnRegister?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
        }



    }
}