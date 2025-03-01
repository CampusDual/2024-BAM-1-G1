package com.vango.presentation.auth.profile

import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import android.widget.Button
import com.vango.databinding.ActivityProfileBinding

class ActivityProfile : AppCompatActivity() {

    // Variables para rastrear el estado de los botones

    private lateinit var binding: ActivityProfileBinding
    private val buttonStates = BooleanArray(4) { false } // Inicialmente todos desactivados
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        // Configurar listeners para cada botón
        setupButton(binding.bOptionA, 0)
        setupButton(binding.bOptionB, 1)
        setupButton(binding.bOptionC, 2)
        setupButton(binding.bOptionD, 3)

    }

    // Método para configurar el listener de un botón
    private fun setupButton(button: Button, index: Int) {
        button.setOnClickListener {
            // Cambiar el estado del botón
            buttonStates[index] = !buttonStates[index]

            // Actualizar la apariencia del botón según su estado
            if (buttonStates[index]) {
                button.setBackgroundColor(Color.GREEN) // Activado
                button.setTextColor(Color.WHITE) // Texto blanco para contraste
                button.text = "Opción ${index + 1} activada"
            } else {
                button.setBackgroundColor(Color.RED) // Activado
                button.setTextColor(Color.WHITE) // Texto blanco para contraste
                button.text = "Opción ${index + 1} desactivada"
            }
        }
    }


}