package com.example.proyectosisvyta_login_parte1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectosisvyta_login_parte1.data.domain.MapaCalorUseCase
import com.example.proyectosisvyta_login_parte1.data.model.MapaCalor
import com.example.proyectosisvyta_login_parte1.data.repository.MapaCalorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapaCalorViewModel: ViewModel() {
    private val repository = MapaCalorRepository()
    private val mapaCalorUseCase = MapaCalorUseCase(repository)

    private val _mapaCalor = MutableStateFlow<List<MapaCalor>>(emptyList())
    val mapaCalor: StateFlow<List<MapaCalor>> = _mapaCalor.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun loadMapaCalor() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = mapaCalorUseCase.getMapaCalor()
            if (result.isSuccess) {
                _mapaCalor.value = result.getOrNull() ?: emptyList()
                _errorMessage.value = null
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Error desconocido"
            }
            _isLoading.value = false
        }
    }

}