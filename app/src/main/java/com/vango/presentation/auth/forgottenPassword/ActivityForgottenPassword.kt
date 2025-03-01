package com.vango.presentation.auth.forgottenPassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.vango.data.dataSource.remote.auth.AuthRemoteGoogleClient
import com.vango.databinding.ActivityForgottenPasswordBinding
import com.vango.presentation.auth.changePass.ActivityChangePass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityForgottenPassword : AppCompatActivity() {
    var binding: ActivityForgottenPasswordBinding? = null
    var viewModel: ActivityForgottenPasswordViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgottenPasswordBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this)[ActivityForgottenPasswordViewModel::class]

        initListeners()
        initObservers()

    }

    private fun initObservers(){
        viewModel?.isResetPasswordSuccess?.observe(this){
                isSuccess ->
            if (isSuccess){
                val intentActivityHome = Intent(this, ActivityChangePass::class.java)
                intentActivityHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentActivityHome)
                finish()
            }
        }

        viewModel?.error?.observe(this) { message ->
            Toast.makeText(this@ActivityForgottenPassword, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.success?.observe(this) { message ->
            Toast.makeText(this@ActivityForgottenPassword, message, Toast.LENGTH_LONG).show()
        }



    }

    private fun initListeners(){
        binding?.etForgottenInputEmail?.doOnTextChanged{
                text,start,before,count ->
            viewModel?.setEmail(text.toString())
        }

        binding?.btForgottenPasswordSignup?.setOnClickListener{
            viewModel?.resetPassword()
        }
    }

}