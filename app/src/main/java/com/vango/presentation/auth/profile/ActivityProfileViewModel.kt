package com.vango.presentation.auth.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _errorProfileNick: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorProfileNick: LiveData<Boolean> = _errorProfileNick

    private val _errorProfileAge: MutableLiveData<Boolean> = MutableLiveData(false)
    var errorProfileAge: LiveData<Boolean> = _errorProfileAge

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

    fun checkValius(): Boolean {
        val errProfileNick: Boolean = checkProfileNick()
        val errProfileAge: Boolean = checkProfileAge()
        val errProfileCountry: Boolean = checkProfileCountry()
        val errProfileProvince: Boolean = checkProfileProvince()

        return errProfileNick && errProfileAge && errProfileCountry && errProfileProvince
    }

    fun checkProfileNick(): Boolean {
        return profilenick.isNotEmpty()
    }

    fun checkProfileAge(): Boolean {
        return profileage.toIntOrNull() != null && profileage.isNotEmpty()
    }

    fun checkProfileCountry(): Boolean {
        return profilecountry != 0
    }

    fun checkProfileProvince(): Boolean {
        return profileprovince != 0
    }
}