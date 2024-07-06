package com.example.proyectosisvyta_login_parte1.data.domain

import com.example.proyectosisvyta_login_parte1.data.model.Usuario
import com.example.proyectosisvyta_login_parte1.data.repository.LoginUsuarioRepository
import retrofit2.Response

class LoginUsuarioUseCase(private val repository: LoginUsuarioRepository) {

    suspend fun loginUsuarioUC(usuario: String, contrasena: String): Result<Usuario>{
        val loginRequest = mapOf("usuario" to usuario, "contrasena" to contrasena)
        return repository.loginUsuarioRepository(loginRequest)
    }
}