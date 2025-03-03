package com.vango.presentation.auth.login

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
import androidx.lifecycle.lifecycleScope
import com.vango.data.dataSource.remote.auth.AuthRemoteGoogleClient
import com.vango.databinding.ActivityLoginBinding
import com.vango.presentation.auth.forgottenPassword.ActivityForgottenPassword
import com.vango.presentation.auth.signup.ActivitySignup
import com.vango.presentation.main.ActivityMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private var viewModel: ActivityLoginViewModel? = null

    @Inject
    lateinit var authRemoteGoogleClient: AuthRemoteGoogleClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this)[ActivityLoginViewModel::class.java]

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel?.isLoginSuccess?.observe(this) { isSuccess ->
            if (isSuccess) {
                val intentActivityHome = Intent(this, ActivityMain::class.java)
                intentActivityHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentActivityHome)
                finish()
            }
        }

        viewModel?.error?.observe(this) { message ->
            Toast.makeText(this@ActivityLogin, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.success?.observe(this) { message ->
            Toast.makeText(this@ActivityLogin, message, Toast.LENGTH_LONG).show()
        }
        viewModel?.isLoading?.observe(this) { isLoading ->
            if (isLoading) showLoading() else hideLoading()
        }
    }

    private fun initListeners() {
        binding?.etLoginInputEmail?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setEmail(text.toString())
        }

        binding?.etLoginInputPassword?.doOnTextChanged { text, _, _, _ ->
            viewModel?.setPassword(text.toString())
        }

        binding?.btLoginButton?.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = currentFocus ?: View(this@ActivityLogin)
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            showLoading()
            viewModel?.login()
        }

        val btnGoogle = binding?.mbGoogle
        btnGoogle?.setOnClickListener {
            showLoading()

            lifecycleScope.launch {
                try {
                    val success = authRemoteGoogleClient.signIn(this@ActivityLogin)
                    if (success) {
                        val intent = Intent(this@ActivityLogin, ActivityMain::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@ActivityLogin,
                            "Error al iniciar sesión con Google",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@ActivityLogin,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    hideLoading()
                }
            }
        }

        val linkSignup = binding?.tvNoLoginRegister
        linkSignup?.setOnClickListener {
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
        }

        val linkForgotPass = binding?.tvLoginForgotPassword
        linkForgotPass?.setOnClickListener {
            val intent = Intent(this, ActivityForgottenPassword::class.java)
            startActivity(intent)
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