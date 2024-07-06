package com.example.proyectosisvyta_login_parte1.data.repository

import com.example.proyectosisvyta_login_parte1.data.model.Estudiante
import com.example.proyectosisvyta_login_parte1.data.model.TestRealizado
import com.example.proyectosisvyta_login_parte1.network.ApiInstance

class TestRealizadosRepository {
    private val api = ApiInstance.apiInstance

    suspend fun getEstudiantes(): Result<List<Estudiante>> = try {
        val response = api.getEstudiantes()
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Error al obtener estudiantes"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getTestsPorUsuario(idUsuario: Int): Result<List<TestRealizado>> = try {
        val response = api.getTestsPorUsuario(idUsuario)
        if (response.isSuccessful) {
            Result.success(response.body()!!)
        }else{
            Result.failure(Exception("Error al obtener tests"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun actualizarPuntajeTest(idTest: Int, puntaje: Int): Result<String> = try {
        val response = api.actualizarTest(idTest, mapOf("puntaje" to puntaje))
        if (response.isSuccessful) {
            Result.success("Puntaje actualizado correctamente")
        }else{
            Result.failure(Exception("Error al actualizar test"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }


}