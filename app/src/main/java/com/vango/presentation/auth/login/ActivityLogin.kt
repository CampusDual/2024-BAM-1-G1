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

        val linkSignup = binding?.tvNoLoginRegister
        linkSignup?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
            finish()
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

        initListeners()
        initObservers()

    }

    private fun initObservers(){
        viewModel?.isLoginSuccess?.observe(this){
            isSuccess ->
            if (isSuccess){
                Toast.makeText(this,"Has iniciado sesión con éxito",Toast.LENGTH_LONG)

            }
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
    }


    //TODO("esto es un test")
}