package com.vango.presentation.main.home

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
    private val _selectedOption = MutableStateFlow(MapOption.WEATHER)
    val selectedLayer: StateFlow<MapLayer> = _selectedLayer.asStateFlow()
    val selectedOption: StateFlow<MapOption> = _selectedOption.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

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
            val location = getUserLocationUseCase()
            if (location != null) {
                _currentLocation.value = location
            }
        }
    }

    fun updateMapLayer(layer: MapLayer) {
        _selectedLayer.value = layer
    }
    fun updateMapOption(option: MapOption) {
        _selectedOption.value = option
    }

}