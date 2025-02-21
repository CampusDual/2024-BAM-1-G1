package com.vango.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.vango.data.dataSource.remote.auth.GoogleSignInClient
import com.vango.databinding.ActivityLoginBinding
import com.vango.domain.entities.AppError
import com.vango.presentation.auth.changePass.ActivityChangePass
import com.vango.presentation.auth.forgottenPassword.ActivityForgottenPassword
import com.vango.presentation.auth.signup.ActivitySignup
import com.vango.presentation.home.ActivityHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityLogin : AppCompatActivity() {
    var binding: ActivityLoginBinding? = null

    private val googleSignInClient by lazy {
        GoogleSignInClient(this)
    }
    var viewModel: ActivityLoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this)[ActivityLoginViewModel::class]

        initListeners()
        initObservers()

    }

    private fun initObservers(){
        viewModel?.isLoginSuccess?.observe(this){
                isSuccess ->
            if (isSuccess){
                val intentActivityHome = Intent(this, ActivityHome::class.java)
                intentActivityHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentActivityHome)
                finish()
            }
        }

        viewModel?.error?.observe(this) { exception ->
            val message = when (exception) {
                is AppError.DetailedError -> exception.body
                else -> "Ha ocurrido un error inesperado, esta vez no es de back"
            }


            Toast.makeText(
                this@ActivityLogin,
                message,
                Toast.LENGTH_LONG
            ).show()


        }

    }

    private fun initListeners(){
        binding?.etLoginInputEmail?.doOnTextChanged{
                text,start,before,count ->
            viewModel?.setEmail(text.toString())
        }

        binding?.etLoginInputPassword?.doOnTextChanged{
                text,start,before,count ->
            viewModel?.setPassword(text.toString())
        }

        binding?.btLoginButton?.setOnClickListener{
            viewModel?.login()
        }

        val btnGoogle = binding?.mbGoogle
        btnGoogle?.setOnClickListener{
            lifecycleScope.launch {
                val success = googleSignInClient.signIn()
                if (success){
                    val intent = Intent(this@ActivityLogin, ActivityHome::class.java)
                    startActivity(intent)
                    finish()

                }
            }

        }

        val linkSignup = binding?.tvNoLoginRegister
        linkSignup?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
            finish()
        }

        val linkForgotPass = binding?.tvLoginForgotPassword
        linkForgotPass?.setOnClickListener{
            val intent = Intent(this, ActivityForgottenPassword::class.java)
            startActivity(intent)
        }
    }

}