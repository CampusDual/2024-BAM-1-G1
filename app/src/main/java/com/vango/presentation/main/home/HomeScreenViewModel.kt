package com.vango.presentation.main.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.vango.domain.usecase.location.GetUserLocationUseCase
import com.vango.presentation.main.home.components.MapLayer
import com.vango.presentation.main.home.components.MapOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUserLocationUseCase: GetUserLocationUseCase
) : ViewModel() {

    private val _currentLocation = MutableStateFlow(LatLng(40.416775, -3.703790))
    val currentLocation: StateFlow<LatLng> = _currentLocation

    private val _selectedLayer = MutableStateFlow(MapLayer.NORMAL)
    val selectedLayer: StateFlow<MapLayer> = _selectedLayer.asStateFlow()

    private val _selectedOption = MutableStateFlow<MapOption?>(null)
    val selectedOption: StateFlow<MapOption?> = _selectedOption.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    private val _hasToRequestPermission = MutableStateFlow(false)
    val hasToRequestPermission: StateFlow<Boolean> = _hasToRequestPermission.asStateFlow()

    var isLocationActive: Boolean = false
        private set

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun performSearch(query: String) {
        if (query.lowercase() == "madrid") {
            _currentLocation.value = LatLng(40.416775, -3.703790)
        }
    }

    fun fetchUserLocation() {
        viewModelScope.launch {
            if (isLocationActive){
                val location = getUserLocationUseCase()
                if (location != null) {
                    _currentLocation.value = location
                } else {
                    TODO("Hacer algo con el error, es decir que no se pudo obtener la ubicación o pedir permisos nuevamente")
                    Log.e("HomeViewModel", "No se pudo obtener la ubicación")
                }
            }else{
                _hasToRequestPermission.value = true
            }


        }
    }

    fun updateMapLayer(layer: MapLayer) {
        _selectedLayer.value = layer
    }

    fun updateMapOption(option: MapOption?) {
        _selectedOption.value = option
    }

    fun setIsLocationActive(isActive: Boolean) {
        isLocationActive = isActive
    }

    fun clearPermissionRequest() {
        _hasToRequestPermission.value = false
    }


}