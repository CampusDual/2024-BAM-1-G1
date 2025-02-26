package com.vango.presentation.auth.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.vango.R
import android.widget.Button
import com.vango.databinding.ActivityProfileBinding

class ActivityProfile<T> : AppCompatActivity() {

    // Variables para rastrear el estado de los botones
    private lateinit var binding: ActivityProfileBinding
    private val buttonStates = BooleanArray(4) { false } // Inicialmente todos desactivados
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        // Configurar listeners para cada botón
        setupButton(binding.bOptionA, 0)
        setupButton(binding.bOptionB, 1)
        setupButton(binding.bOptionC, 2)
        setupButton(binding.bOptionD, 3)
    }

    // Método para configurar el listener de un botón
    @SuppressLint("ResourceAsColor")
    private fun setupButton(button: Button, index: Int) {
        button.setOnClickListener {
            // Cambiar el estado del botón
            buttonStates[index] = !buttonStates[index]

            // Actualizar la apariencia del botón según su estado
            if (buttonStates[index]) {
                button.setBackgroundColor(R.color.activated_color) // Activado
                button.text = "Opción ${index + 1} activada"
            } else {
                button.setBackgroundColor(R.color.deactivated_color) // Desactivado
                button.text = "Opción ${index + 1} desactivada"
            }
        }
    }
}