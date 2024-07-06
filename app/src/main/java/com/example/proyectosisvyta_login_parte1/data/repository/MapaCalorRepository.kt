package com.example.proyectosisvyta_login_parte1.data.repository

import com.example.proyectosisvyta_login_parte1.data.model.MapaCalor
import com.example.proyectosisvyta_login_parte1.network.ApiInstance
import java.io.IOException
import java.lang.Exception

class MapaCalorRepository {
    private val service = ApiInstance.apiInstance

    suspend fun getMapaCalor(): Result<List<MapaCalor>> {
        return try {
            val response = service.getMapaCalor()
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(IOException("Error al obtener el mapa de calor: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}