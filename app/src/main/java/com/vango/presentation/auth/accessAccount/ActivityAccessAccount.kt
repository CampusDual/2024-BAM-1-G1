package com.vango.presentation.auth.accessAccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.vango.databinding.ActivityAccessAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityAccessAccount : AppCompatActivity() {
    private var binding: ActivityAccessAccountBinding? = null
    private var viewModel: AccessAcountViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAccessAccountBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[AccessAcountViewModel::class.java]
        setContentView(binding?.root)
    }
}