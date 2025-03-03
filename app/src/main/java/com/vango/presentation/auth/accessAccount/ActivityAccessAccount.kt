package com.vango.presentation.auth.accessAccount

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vango.data.dataSource.remote.auth.AuthRemoteGoogleClient
import com.vango.databinding.ActivityAccessAccountBinding
import com.vango.presentation.auth.login.ActivityLogin
import com.vango.presentation.auth.signup.ActivitySignup
import com.vango.presentation.main.ActivityMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ActivityAccessAccount : AppCompatActivity() {
    private var binding: ActivityAccessAccountBinding? = null
    @Inject
    lateinit var authRemoteGoogleClient: AuthRemoteGoogleClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccessAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val btnLogin = binding?.btAccessAccountSignup
        btnLogin?.setOnClickListener{
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }

        val btnRegister = binding?.btAccessAccountLogin
        btnRegister?.setOnClickListener{
            val intent = Intent(this, ActivitySignup::class.java)
            startActivity(intent)
        }

        val btnGoogle = binding?.mbGoogle
        btnGoogle?.setOnClickListener{
            lifecycleScope.launch {
                val success = authRemoteGoogleClient.signIn(this@ActivityAccessAccount)
                if (success){
                    val intent = Intent(this@ActivityAccessAccount, ActivityMain::class.java)
                    startActivity(intent)
                    finish()

                }
            }

        }

    }

}