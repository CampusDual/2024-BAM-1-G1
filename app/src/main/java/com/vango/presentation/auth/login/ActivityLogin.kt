package com.vango.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

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

        initListeners()
        initObservers()

        val linkSignup = binding?.tvNoLoginRegister
        linkSignup?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
            finish()
        }

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
}