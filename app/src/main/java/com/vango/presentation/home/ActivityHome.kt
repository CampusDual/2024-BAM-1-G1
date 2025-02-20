package com.vango.presentation.home

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.vango.R
import com.vango.databinding.ActivityHomeBinding
import com.vango.presentation.auth.login.ActivityLoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityHome : AppCompatActivity() {
    var binding : ActivityHomeBinding? = null
    var viewModel : ActivityHomeViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this)[ActivityHomeViewModel::class]


    }
}