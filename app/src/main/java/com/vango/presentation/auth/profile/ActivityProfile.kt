package com.vango.presentation.auth.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.button.MaterialButton
import com.hbb20.CountryCodePicker
import com.vango.R
import com.vango.databinding.ActivityProfileBinding
import com.vango.presentation.main.ActivityMain

class ActivityProfile : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var viewModel: ActivityProfileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ActivityProfileViewModel::class.java]

        val countryPicker = findViewById<CountryCodePicker>(binding.ccpProfileInputCountry.id)
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
        initObservers()

        binding.spinnerProfileProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedProvince = parent.getItemAtPosition(position).toString()

                    // Convertir a enum según el país seleccionado
                    val countryCode = countryPicker.selectedCountryNameCode
                    when (countryCode) {
                        "ES" -> {
                            val spanishProvince = SpanishProvinces.fromString(selectedProvince)
                            viewModel.updateProvince(spanishProvince?.id ?: 0)
                            //   val selectedProvinceId = spanishProvince?.id
                            // val selectedProvinceNamebyID = SpanishProvinces.fromId(selectedProvinceId ?: 0)
                            //  Log.d("ActivityProfile", "Provincia ID = ${selectedProvinceId}, Provincia seleccionada: $selectedProvinceNamebyID $selectedProvince")
                        }

                        "PT" -> {
                            val portugueseRegion = PortugueseRegions.fromString(selectedProvince)
                            viewModel.updateProvince(portugueseRegion?.ordinal ?: 0)
                        }

                        "FR" -> {
                            val frenchRegion = FrenchRegions.fromString(selectedProvince)
                            viewModel.updateProvince(frenchRegion?.ordinal ?: 0)
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }


    private fun initObservers() {
        /*  viewModel.errorProfileNick.observe(this) { hasError ->
              binding.etProfileInputNick.setTextColor(
                  getResources().getColor(
                      if (hasError) R.color.color_secondary_wine else R.color.black,
                      null
                  )
              )
          }*/

        viewModel?.errorProfileNick?.observe(this) { (hasError, errorMessage) ->
            binding?.tilProfileInputNick?.error = errorMessage
            binding?.tilProfileInputNick?.isErrorEnabled = hasError
        }



        viewModel.errorProfileAge.observe(this) { (hasError, errorMessage) ->
            binding?.tilProfileInputAge?.error = errorMessage
            binding?.tilProfileInputAge?.isErrorEnabled = hasError
        }


        viewModel.errorProfileCountry.observe(this) { hasError ->
            binding.ccpProfileInputCountry.setBackgroundColor(
                getResources().getColor(
                    if (hasError) R.color.color_secondary_wine else R.color.white,
                    null
                )
            )
        }


        viewModel.errorProfileProvince.observe(this) { hasError ->
            binding.spinnerProfileProvince.setBackgroundColor(
                getResources().getColor(
                    if (hasError) R.color.color_secondary_wine else R.color.white,
                    null
                )
            )
        }

        viewModel.checkValius.observe(this) { isSuccess ->
            if (isSuccess) {
                val intentActivityHome = Intent(this, ActivityMain::class.java)
                //ActivityMain::class.java)
                intentActivityHome.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intentActivityHome)
                finish()
            }
        }
    }

    private fun initListeners() {
        binding?.etProfileInputNick?.doOnTextChanged { text, _, _, _ ->
            viewModel?.updateNick(text.toString())
            viewModel.checkProfileNick()
        }

        binding?.etProfileInputAge?.doOnTextChanged { text, _, _, _ ->
            viewModel?.updateAge(text.toString())
            viewModel.checkProfileAge()
        }
        binding?.btSabeButton?.setOnClickListener {
            viewModel.checkValius()
        }
    }


    private fun setupButton(button: MaterialButton, index: Int) {
        button.setOnClickListener {
            viewModel.toggleButtonState(index)
            viewModel.getButtonStateBinare(index)
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