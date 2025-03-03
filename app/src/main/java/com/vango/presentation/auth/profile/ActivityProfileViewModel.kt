package com.vango.presentation.auth.profile

import androidx.lifecycle.ViewModel

class ActivityProfileViewModel() : ViewModel() {

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


    fun getProvincesByCountryNameCode(countryCode: String): List<String>? {
        return provincesByCountry[countryCode]
    }

    // Estado de los botones
    private val buttonStates = BooleanArray(4) { false }

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

    fun saveProfile() {

    }

    fun toggleButtonState(index: Int) {
        buttonStates[index] = !buttonStates[index]
    }

    fun getButtonState(index: Int): Boolean {
        return buttonStates[index]
    }

}