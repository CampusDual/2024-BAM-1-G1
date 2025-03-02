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
        "ES" to listOf(
            "Álava", "Albacete", "Alicante", "Almería", "Asturias", "Ávila",
            "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Cantabria",
            "Castellón", "Ceuta", "Ciudad Real", "Córdoba", "Cuenca", "Girona",
            "Granada", "Guadalajara", "Guipúzcoa", "Huelva", "Huesca", "Islas Baleares",
            "Jaén", "La Rioja", "Las Palmas", "León", "Lleida", "Lugo", "Madrid",
            "Málaga", "Melilla", "Murcia", "Navarra", "Ourense", "Palencia",
            "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia",
            "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia",
            "Valladolid", "Vizcaya", "Zamora", "Zaragoza"
        ),

        // Regiones de Portugal
        "PT" to listOf(
            "Aveiro", "Beja", "Braga", "Bragança", "Castelo Branco", "Coimbra",
            "Évora", "Faro", "Guarda", "Leiria", "Lisboa", "Portalegre", "Porto",
            "Santarém", "Setúbal", "Viana do Castelo", "Vila Real", "Viseu",
            "Región Autónoma de Azores", "Región Autónoma de Madeira"
        ),

        // Regiones de Francia
        "FR" to listOf(
            "Alsacia", "Aquitania", "Auvernia-Ródano-Alpes", "Bretaña", "Borgoña-Franco Condado",
            "Centro-Valle de Loira", "Champagne-Ardenas", "Córcega", "Gran Este", "Normandía",
            "Nueva Aquitania", "Occitania", "Pays de la Loire", "Provenza-Alpes-Costa Azul",
            "Isla de Francia", "Guyaña Francesa", "Guadalupe", "Martinica", "Reunión", "Mayotte"
        )
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