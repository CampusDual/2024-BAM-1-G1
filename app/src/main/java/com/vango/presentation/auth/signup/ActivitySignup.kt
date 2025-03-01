package com.vango.presentation.auth.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.vango.data.dataSource.remote.auth.AuthRemoteGoogleClient
import com.vango.databinding.ActivitySignupBinding
import com.vango.presentation.auth.login.ActivityLogin
import com.vango.presentation.auth.verifyAccount.ActivityVerifyAccount
import com.vango.presentation.home.ActivityHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivitySignup : AppCompatActivity() {
    private var binding: ActivitySignupBinding? = null

    @Inject
    lateinit var authRemoteGoogleClient: AuthRemoteGoogleClient

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

        val btnGoogle = binding?.mbGoogle
        btnGoogle?.setOnClickListener {
            lifecycleScope.launch {
                val success = authRemoteGoogleClient.signIn(this@ActivitySignup)
                if (success) {
                    val intent = Intent(this@ActivitySignup, ActivityHome::class.java)
                    startActivity(intent)
                    finish()
                }
            }
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

        viewModel?.isSignUpSuccessful?.observe(this) { isSuccess ->
            if(isSuccess){
                val intent = Intent(this, ActivityVerifyAccount::class.java)
                startActivity(intent)
            }
        }

        viewModel?.error?.observe(this) { message ->
            Toast.makeText(this@ActivitySignup, message, Toast.LENGTH_LONG).show()
        }

    }

    private fun initListeners() {
        with(binding) {
            this?.tilSignupInputEmail?.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel?.setEmail(this.tilSignupInputEmail.editText?.text.toString())
                }
            }

            this?.tilSignupInputPassword?.editText?.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    viewModel?.setPassword(this.tilSignupInputPassword.editText?.text.toString())
                }
            }

            this?.tilSignupInputConfirmPassword?.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel?.setConfirmPassword(text.toString())
            }
            this?.btSignupButton?.setOnClickListener {
                viewModel?.signUp()
            }
        }
    }
}
