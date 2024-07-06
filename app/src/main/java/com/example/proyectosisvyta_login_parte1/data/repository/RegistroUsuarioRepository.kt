package com.example.proyectosisvyta_login_parte1.data.repository

import android.util.Log
import com.example.proyectosisvyta_login_parte1.data.model.RegistroUsuario
import com.example.proyectosisvyta_login_parte1.network.ApiInstance
import java.lang.Exception

class RegistroUsuarioRepository {
    private val service = ApiInstance.apiInstance

    suspend fun registerUsuarioRepository(
        registerRequest: RegistroUsuario ): Result<RegistroUsuario>{
        return try{
            val response = service.registerUsuarioApiService(registerRequest)
            if(response.isSuccessful && response.body()!=null){
                Result.success(response.body()!!)
            }else{
                Log.e("RegistroUsuarioRepository", "Error en el registro: ${response.code()} - ${response.message()}")
                Result.failure(Exception("Er: ${response.code()} - ${response.message()}"))
            }
        }catch (e: Exception){
            Log.e("RegistroUsuarioRepository", "Error en la comunicacion con el servidor", e)
            Result.failure(e)
        }
    }

}

