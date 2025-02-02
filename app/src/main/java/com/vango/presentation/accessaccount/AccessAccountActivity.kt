package com.vango.presentation.accessaccount

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.vango.databinding.ActivityAccessAccountBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccessAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccessAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAccessAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = binding.navHostFragment.getFragment<NavHostFragment>()
        val navController = navHostFragment.navController

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
