package com.vango.presentation.auth.profile

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.hbb20.CountryCodePicker
import com.vango.R
import com.vango.databinding.ActivityProfileBinding

class ActivityProfile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ActivityProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ActivityProfileViewModel::class.java]

        val countryPicker = findViewById<CountryCodePicker>(R.id.ccp_profile_input_country)
        countryPicker.setOnCountryChangeListener {
            viewModel.updateCountry(countryPicker.selectedCountryCode)
            updateProvinces(countryPicker.selectedCountryNameCode)
            Toast.makeText(
                this,
                "Cargar Provincias ${countryPicker.selectedCountryName}",
                Toast.LENGTH_SHORT
            ).show()
        }
        setupButton(binding.bOptionA, 0)
        setupButton(binding.bOptionB, 1)
        setupButton(binding.bOptionC, 2)
        setupButton(binding.bOptionD, 3)

        updateButtonAppearance(binding.bOptionA, 0)
        updateButtonAppearance(binding.bOptionB, 1)
        updateButtonAppearance(binding.bOptionC, 2)
        updateButtonAppearance(binding.bOptionD, 3)

        updateProvinces(countryPicker.selectedCountryNameCode)

        initListeners()

        binding.spinnerProfileProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateProvince(position)
                }
                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun initListeners() {
        binding?.etProfileInputNick?.doOnTextChanged { text, _, _, _ ->
            viewModel?.updateNick(text.toString())
        }

        binding?.etProfileInputAge?.doOnTextChanged { text, _, _, _ ->
            viewModel?.updateAge(text.toString())
        }

        binding?.ccpProfileInputCountry?.setOnCountryChangeListener {
            viewModel?.updateCountry(binding?.ccpProfileInputCountry?.selectedCountryCode.toString())
        }



        binding?.btSabeButton?.setOnClickListener {
            viewModel?.saveProfile()
        }
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
            button.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.color_main)
            button.setTextColor(Color.WHITE)
        } else {
            button.backgroundTintList =
                ContextCompat.getColorStateList(this, R.color.deactivated_color)
            button.setTextColor(Color.WHITE)
        }
    }

    private fun updateProvinces(countryNameCode: String) {
        val provinceSpinner = binding?.spinnerProfileProvince
        val provinces = viewModel.getProvincesByCountryNameCode(countryNameCode) ?: emptyList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, provinces)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner?.adapter = adapter
    }

}