package com.vango.presentation.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.vango.domain.model.SearchResult
import com.vango.domain.usecase.location.GetUserLocationUseCase
import com.vango.domain.usecase.places.SearchPlacesUseCase
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
    private val getUserLocationUseCase: GetUserLocationUseCase,
    private val searchPlacesUseCase: SearchPlacesUseCase
) : ViewModel() {

    private val _currentLocation = MutableStateFlow(LatLng(40.416775, -3.703790))
    val currentLocation: StateFlow<LatLng> = _currentLocation

    private val _selectedLayer = MutableStateFlow(MapLayer.NORMAL)
    val selectedLayer: StateFlow<MapLayer> = _selectedLayer.asStateFlow()

    private val _selectedOption = MutableStateFlow<MapOption?>(null)
    val selectedOption: StateFlow<MapOption?> = _selectedOption.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _hasToRequestPermission = MutableStateFlow(false)
    val hasToRequestPermission: StateFlow<Boolean> = _hasToRequestPermission.asStateFlow()

    private val _searchResults = MutableStateFlow<List<SearchResult>>(emptyList())
    val searchResults: StateFlow<List<SearchResult>> = _searchResults.asStateFlow()

    var isLocationActive: Boolean = false
        private set

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        performSearch(query)
    }

    fun performSearch(query: String) {
        if (query.isBlank()) {
            _searchResults.value = emptyList()
            return
        }
        viewModelScope.launch {
            try {
                val results = searchPlacesUseCase(query, _currentLocation.value).map {
                    SearchResult(it.name, it.latitude, it.longitude, it.placeId, it.secondaryText, it.distanceMeters)
                }
                _searchResults.value = results
            } catch (e: Exception) {
                _errorMessage.value = "Error al buscar: ${e.message}"
            }
        }
    }

    fun selectSearchResult(result: SearchResult) {
        viewModelScope.launch {
            try {
                val detailedResult = result.placeId?.let { searchPlacesUseCase.getPlaceDetails(it) }
                if (detailedResult != null) {
                    _currentLocation.value = searchPlacesUseCase.toLatLng(detailedResult)
                    _searchResults.value = emptyList()
                    _searchQuery.value = detailedResult.name
                } else {
                    _errorMessage.value = "No se pudieron obtener los detalles del lugar."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error al obtener detalles: ${e.message}"
            }
        }
    }

    fun fetchUserLocation() {
        viewModelScope.launch {
            val location = getUserLocationUseCase()
            if (location != null) {
                _currentLocation.value = location
            } else {
                _errorMessage.value = "Se necesitan permisos de ubicación para obtener tu posición actual."
                _hasToRequestPermission.value = true
                _currentLocation.value = LatLng(40.416775, -3.703790)
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }

    fun clearPermissionRequest() {
        _hasToRequestPermission.value = false
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


}