package com.example.proyectosisvyta_login_parte1.data.domain

import com.example.proyectosisvyta_login_parte1.data.model.MapaCalor
import com.example.proyectosisvyta_login_parte1.data.repository.MapaCalorRepository

class MapaCalorUseCase(private val repository: MapaCalorRepository) {

    suspend fun getMapaCalor(): Result<List<MapaCalor>>{
        return repository.getMapaCalor()
    }

}