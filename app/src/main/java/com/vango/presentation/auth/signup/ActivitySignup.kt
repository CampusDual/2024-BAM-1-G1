package com.vango.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.vango.R
import com.vango.databinding.ActivitySignupBinding
import com.vango.presentation.auth.login.ActivityLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySignup : AppCompatActivity() {
    var binding: ActivitySignupBinding? = null
    private var viewModel: ActivitySignupViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ActivitySignupViewModel::class.java]
        setContentView(binding?.root)

        val linkLogin = binding?.tvNoSignupRegister
        linkLogin?.setOnClickListener {
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
            finish()
        }
        initObserver()
        initListener()
    }

    private fun initObserver() {
        viewModel?.errorEmail?.observe(this) { hasError ->
            binding?.tilSignupInputEmail?.error = "Email invalido"

            binding?.tilSignupInputEmail?.isErrorEnabled = hasError
        }

    }

    private fun initListener() {
        with(binding) {
            this?.etSignupInputEmail?.doOnTextChanged{
                    text, start, before, count ->
                viewModel?.setEmail(text.toString())
            }
        }


    }    }
