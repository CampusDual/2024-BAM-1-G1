package com.vango.presentation.auth.verifyAccount

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vango.databinding.ActivityVerifyAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityVerifyAccount : AppCompatActivity() {
    private var binding: ActivityVerifyAccountBinding? = null
    private var viewModel: ActivityVerifyAccountViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityVerifyAccountBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel = ViewModelProvider(this)[ActivityVerifyAccountViewModel::class.java]

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        viewModel?.error?.observe(this) { message ->
            Toast.makeText(this@ActivityVerifyAccount, message, Toast.LENGTH_LONG).show()
        }

        viewModel?.success?.observe(this) { message ->
            Toast.makeText(this@ActivityVerifyAccount, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun initListeners() {


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}