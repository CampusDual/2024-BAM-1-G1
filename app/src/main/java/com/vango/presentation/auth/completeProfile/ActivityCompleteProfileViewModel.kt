package com.vango.presentation.auth.completeProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityCompleteProfileViewModel() : ViewModel() {

    // Datos del perfil
    private var profilenick: String = ""
    private var profileage: String = ""
    private var profilecountry: Int = 0
    private var profileprovince: Int = 0

    private val provincesByCountry = mapOf(
        // Provincias de España
        "ES" to SpanishProvinces.values().map { it.name },

        // Regiones de Portugal
        "PT" to PortugueseRegions.values().map { it.name },

        // Regiones de Francia
        "FR" to FrenchRegions.values().map { it.name }
    )
    private val _errorProfileNick: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    var errorProfileNick: LiveData<Pair<Boolean, String>> = _errorProfileNick

    private val _errorProfileAge: MutableLiveData<Pair<Boolean, String>> = MutableLiveData()
    var errorProfileAge: LiveData<Pair<Boolean, String>> = _errorProfileAge

    private val _errorProfileCountry: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorProfileCountry: LiveData<Boolean> = _errorProfileCountry

    private val _errorProfileProvince: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorProfileProvince: LiveData<Boolean> = _errorProfileProvince

    private val _checkValius: MutableLiveData<Boolean> = MutableLiveData(false)
    var checkValius: LiveData<Boolean> = _checkValius


    fun getProvincesByCountryNameCode(countryCode: String): List<String>? {
        return provincesByCountry[countryCode]
    }

    // Estado de los botones

    private val buttonStates = BooleanArray(4) { false }

    fun toggleButtonState(index: Int) {
        buttonStates[index] = !buttonStates[index]
    }

    fun getButtonState(index: Int): Boolean {
        return buttonStates[index]
    }

    var buttonStateBinare = mutableListOf(0, 0, 0, 0)

    fun getButtonStateBinare(index: Int){
        if (buttonStates[index]) {
           buttonStateBinare [index] = 1
        }else buttonStateBinare [index] = 0
    }


    // Actualización de los datos del perfil
    fun updateNick(nick: String) {
        profilenick = nick
    }

    fun updateAge(age: String) {
        profileage = age
    }

    fun updateCountry(countryCode: String) {
        val countryInt: Int = countryCode.toInt()
        profilecountry = countryInt
    }

    fun updateProvince(position: Int) {
        profileprovince = position
    }

    //Funciones de validación

    fun checkValius() {
        val errProfileNick: Boolean = checkProfileNick()
        val errProfileAge: Boolean = checkProfileAge()
        _checkValius.value = errProfileNick && errProfileAge
    }

    fun checkProfileNick(): Boolean {
        var errorMessages = mutableListOf<String>()
        var error = false
        if (profilenick.isEmpty()) {
            errorMessages = (errorMessages + "El nick no puede estar vacío").toMutableList()
            error = true
        }
        if (profilenick.length < 3) {
            errorMessages =
                (errorMessages + "El nick debe tener al menos 3 caracteres").toMutableList()
            error = true
        }
        if (profilenick.length > 20) {
            errorMessages =
                (errorMessages + "El nick debe tener como máximo 20 caracteres").toMutableList()
            error = true
        }
        if (profilenick.contains(" ")) {
            errorMessages =
                (errorMessages + "El nick no puede contener espacios").toMutableList()
            error = true
        }
        val forbiddenWords = ForbiddenWords.values()
        if (forbiddenWords.any { profilenick.contains(it.name, ignoreCase = true) }) {
            errorMessages = (errorMessages + "Contenido Inaptopiado").toMutableList()
            error = true
        }
        if (error) {
            _errorProfileNick.value = Pair(true, errorMessages.joinToString(". "))
            return false
        } else {
            _errorProfileNick.value = Pair(false, "")
            return true
        }


    }

    fun checkProfileAge(): Boolean {
        var errorMessages = mutableListOf<String>()
        var error = false
        if (profileage.isEmpty()) {
            errorMessages = (errorMessages + "La edad no puede estar vacía").toMutableList()
            error = true
        }
        if (profileage.toIntOrNull() == null) {
            errorMessages = (errorMessages + "La edad debe ser un número").toMutableList()
            error = true
        } else if (profileage.toInt() < 18) {
            errorMessages =
                (errorMessages + "La edad debe ser mayor de 18 años").toMutableList()
            error = true
        } else if (profileage.toInt() > 150) {
            errorMessages =
                (errorMessages + "La edad debe ser menor de 100 años").toMutableList()
            error = true
        }
        if (error) {
            _errorProfileAge.value = Pair(true, errorMessages.joinToString(". "))
            return false
        } else {
            _errorProfileAge.value = Pair(false, "")
            return true
        }

    }

}