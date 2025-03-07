package com.vango.presentation.auth.verifyAccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.vango.databinding.ActivityVerifyAccountBinding
import com.vango.presentation.auth.profile.ActivityProfile
import com.vango.presentation.main.ActivityMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityVerifyAccount : AppCompatActivity() {
    private var binding: ActivityVerifyAccountBinding? = null
    private var viewModel: ActivityVerifyAccountViewModel? = null
    private var firebaseIdReceived: String? = null
    private var emailReceived: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerifyAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        firebaseIdReceived = intent.getStringExtra("firebaseId") ?: ""
        emailReceived = intent.getStringExtra("email") ?: ""

        viewModel = ViewModelProvider(this)[ActivityVerifyAccountViewModel::class.java]

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel?.isAccountVerified?.observe(this) { isSuccess ->
            if (isSuccess) {
                val intentActivityHome = Intent(this, ActivityProfile::class.java)
                    //ActivityMain::class.java)
                intentActivityHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentActivityHome)
                finish()
            }
        }
        viewModel?.error?.observe(this) { message ->
            hideLoading()

            Toast.makeText(this@ActivityVerifyAccount, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.success?.observe(this) { message ->
            hideLoading()

            Toast.makeText(this@ActivityVerifyAccount, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.isLoading?.observe(this) { isLoading ->
            if (isLoading) showLoading() else hideLoading()
        }
        viewModel?.resendTimer?.observe(this) { time ->
            if (time >= 0) {
                binding?.timeResend?.visibility = View.VISIBLE
                binding?.timeResend?.text = "Por favor espere $time segundos antes de volver a enviar el código"
            } else {
                binding?.timeResend?.visibility = View.GONE
            }
        }

        viewModel?.isResendEnabled?.observe(this) { enabled ->
            binding?.tvLoginForgotPassword?.isEnabled = enabled
            binding?.tvLoginForgotPassword?.alpha = if (enabled) 1.0f else 0.5f
        }
    }

    private fun initListeners() {
        val editText1 = binding?.etVerificationCode1
        val editText2 = binding?.etVerificationCode2
        val editText3 = binding?.etVerificationCode3
        val editText4 = binding?.etVerificationCode4

        editText1?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setTextOne(text.toString())
            if (text?.length == 1) {
                editText2?.requestFocus()
            }
        }

        editText2?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setTextTwo(text.toString())
            if (text?.length == 1) {
                editText3?.requestFocus()
            }
        }

        editText3?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setTextThree(text.toString())
            if (text?.length == 1) {
                editText4?.requestFocus()
            }
        }

        editText4?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setTextFour(text.toString())
            if (text?.length == 1) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText4.windowToken, 0)
            }
        }

        binding?.btSignupButton?.setOnClickListener {
            showLoading()
            viewModel?.verifyCode(firebaseIdReceived.toString())
        }

        binding?.tvLoginForgotPassword?.setOnClickListener {
            viewModel?.resendEmailVerification(emailReceived, firebaseIdReceived)
        }

    }

    private fun showLoading() {
        binding?.loadingContainer?.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding?.loadingContainer?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}