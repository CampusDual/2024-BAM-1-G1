package com.vango.presentation.auth.signup

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
import com.vango.databinding.ActivitySignupBinding
import com.vango.presentation.auth.login.ActivityLogin
import com.vango.presentation.auth.verifyAccount.ActivityVerifyAccount
import com.vango.presentation.main.ActivityMain
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
        }

        val btnGoogle = binding?.mbGoogle
        btnGoogle?.setOnClickListener {
            showLoading()

            lifecycleScope.launch {
                try {
                    val success = authRemoteGoogleClient.signIn(this@ActivitySignup)
                    if (success) {
                        val intent = Intent(this@ActivitySignup, ActivityMain::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this@ActivitySignup,
                            "Error al iniciar sesión con Google",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this@ActivitySignup,
                        "Error: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                } finally {
                    hideLoading()
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
            hideLoading()
            if (isSuccess) {
                val firebaseId = viewModel?.firebaseId?.value
                val email = viewModel?.email?.value
                val intent = Intent(this, ActivityVerifyAccount::class.java)
                intent.putExtra("firebaseId", firebaseId)
                intent.putExtra("email", email)

                startActivity(intent)
            }
        }

        viewModel?.error?.observe(this) { message ->
            hideLoading()
            Toast.makeText(this@ActivitySignup, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.isLoading?.observe(this) { isLoading ->
            if (isLoading) showLoading() else hideLoading()
        }

    }

    private fun initListeners() {
        with(binding) {
            this?.tilSignupInputEmail?.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel?.setEmail(this.tilSignupInputEmail.editText?.text.toString())
            }

            this?.tilSignupInputPassword?.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel?.setPassword(this.tilSignupInputPassword.editText?.text.toString())
            }

            this?.tilSignupInputConfirmPassword?.editText?.doOnTextChanged { text, _, _, _ ->
                viewModel?.setConfirmPassword(text.toString())
            }
            this?.btSignupButton?.setOnClickListener {

                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val view = currentFocus ?: View(this@ActivitySignup)
                imm.hideSoftInputFromWindow(view.windowToken, 0)

                showLoading()
                viewModel?.signUp()
            }

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
