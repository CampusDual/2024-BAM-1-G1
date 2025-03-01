package com.vango.presentation.auth.profile
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.vango.R
import com.vango.databinding.ActivityProfileBinding

class ActivityProfile : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var viewModel: ActivityProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ActivityProfileViewModel::class.java]
        setupButton(binding.bOptionA, 0)
        setupButton(binding.bOptionB, 1)
        setupButton(binding.bOptionC, 2)
        setupButton(binding.bOptionD, 3)

        updateButtonAppearance(binding.bOptionA, 0)
        updateButtonAppearance(binding.bOptionB, 1)
        updateButtonAppearance(binding.bOptionC, 2)
        updateButtonAppearance(binding.bOptionD, 3)


    }


    private fun setupButton(button: MaterialButton, index: Int) {
        button.setOnClickListener {
            viewModel.toggleButtonState(index)
            updateButtonAppearance(button, index)
        }
    }


    private fun updateButtonAppearance(button: MaterialButton, index: Int) {
        val isActivated = viewModel.getButtonState(index)
        if (isActivated) {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.activated_color) // Activado
            button.setTextColor(Color.WHITE)
        } else {
            button.backgroundTintList = ContextCompat.getColorStateList(this, R.color.deactivated_color) // Desactivado
            button.setTextColor(Color.WHITE)
        }
    }

}