package com.vango.presentation.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.vango.databinding.ActivityMainBinding
import com.vango.presentation.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        observeAuthentication()
        mainViewModel.checkUserAuthentication()
    }

    private fun observeAuthentication() {
        lifecycleScope.launch {
            mainViewModel.isUserAuthenticated.collect { isAuthenticated ->
                if (isAuthenticated == false) {
                    navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginFragment::class.java)
        startActivity(intent)
        finish()
    }
}
