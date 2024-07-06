package com.example.proyectosisvyta_login_parte1.data.domain

import com.example.proyectosisvyta_login_parte1.data.repository.TestRealizadosRepository
import com.example.proyectosisvyta_login_parte1.data.repository.TestRepository

class TestRealizadosUseCase {
    private val repository = TestRealizadosRepository()

    suspend fun getEstudiantes() = repository.getEstudiantes()

    suspend fun getTestsPorUsuario(idUsuario: Int) = repository.getTestsPorUsuario(idUsuario)

    suspend fun actualizarPuntajeTest(idTest: Int, puntaje: Int) = repository.actualizarPuntajeTest(idTest, puntaje)
}