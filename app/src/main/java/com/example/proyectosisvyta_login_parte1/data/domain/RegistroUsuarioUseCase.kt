package com.example.proyectosisvyta_login_parte1.data.domain

import com.example.proyectosisvyta_login_parte1.data.model.RegistroUsuario
import com.example.proyectosisvyta_login_parte1.data.repository.RegistroUsuarioRepository
import retrofit2.Response

class RegistroUsuarioUseCase(private val repository: RegistroUsuarioRepository) {

    suspend fun registerUsuarioUC(
        registroUsuario: RegistroUsuario
    ): Result<RegistroUsuario>{
        return repository.registerUsuarioRepository(registroUsuario)
    }
}