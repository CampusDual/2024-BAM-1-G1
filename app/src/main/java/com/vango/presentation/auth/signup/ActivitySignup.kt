package com.vango.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.vango.R
import com.vango.databinding.ActivitySignupBinding
import com.vango.presentation.auth.login.ActivityLogin
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivitySignup : AppCompatActivity() {
    var binding: ActivitySignupBinding? = null
    var viewModel: ActivitySignupViewModel? = null
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

        initListeners()
        initObservers()


    }

    private fun initObservers() {
        viewModel?.errorEmail?.observe(this) { (hasError, errorMessage) ->
            binding?.tilSignupInputEmail?.error = errorMessage
            binding?.tilSignupInputEmail?.isErrorEnabled = hasError

        }

        viewModel?.errorPassword?.observe(this) { (hasError, errorMessage) ->
            binding?.tilSignupInputPassword?.error = errorMessage
            binding?.tilSignupInputPassword?.isErrorEnabled = hasError
        }

        viewModel?.errorConfirmPassword?.observe(this) { (hasError, errorMessage) ->
            binding?.tilSignupInputConfirmPassword?.error = errorMessage
            binding?.tilSignupInputConfirmPassword?.isErrorEnabled = hasError
        }

    }

    private fun initListeners() {
        with(binding) {
            this?.tilSignupInputEmail?.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel?.setEmail(this?.tilSignupInputEmail?.editText?.text.toString())
                }
            }

            this?.tilSignupInputPassword?.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel?.setPassword(this?.tilSignupInputPassword?.editText?.text.toString())
                }
            }

            this?.tilSignupInputConfirmPassword?.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel?.setConfirmPassword(this?.tilSignupInputConfirmPassword?.editText?.text.toString())
                }
            }
            this?.btSignupButton?.setOnClickListener {
                viewModel?.signUp()
            }


        }
    }
}
