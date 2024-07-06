package com.example.proyectosisvyta_login_parte1.data.repository

import com.example.proyectosisvyta_login_parte1.data.model.Usuario
    import com.example.proyectosisvyta_login_parte1.network.ApiInstance
    import retrofit2.Response
    import java.io.IOException
    import java.lang.Exception


class LoginUsuarioRepository {
    private val service = ApiInstance.apiInstance

    suspend fun loginUsuarioRepository(loginRequest : Map<String, String>): Result<Usuario>{
        return try{
            val response = service.loginUsuarioApiService(loginRequest)
            if(response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(IOException("Error al iniciar sesion: ${response.code()} ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}