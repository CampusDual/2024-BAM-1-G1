package com.vango.presentation.auth.accessAccount

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import com.vango.data.dataSource.remote.auth.GoogleSignInClient
import com.vango.databinding.ActivityAccessAccountBinding
import com.vango.presentation.auth.login.ActivityLogin
import com.vango.presentation.auth.signup.ActivitySignup
import com.vango.presentation.home.ActivityHome
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivityAccessAccount : AppCompatActivity() {
    private var binding: ActivityAccessAccountBinding? = null

    private val googleSignInClient by lazy {
        GoogleSignInClient(this)
    }
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
                val success = googleSignInClient.signIn()
                if (success){
                    val intent = Intent(this@ActivityAccessAccount, ActivityHome::class.java)
                    startActivity(intent)
                    finish()

                }
            }

        }

    }

}