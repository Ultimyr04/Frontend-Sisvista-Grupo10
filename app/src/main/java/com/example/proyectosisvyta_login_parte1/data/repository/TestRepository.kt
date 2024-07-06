package com.example.proyectosisvyta_login_parte1.data.repository

import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Question
import com.example.proyectosisvyta_login_parte1.data.model.TestPuntaje
import com.example.proyectosisvyta_login_parte1.data.model.TestRespuesta
import com.example.proyectosisvyta_login_parte1.network.ApiInstance
import java.lang.Exception
import retrofit2.Response

class TestRepository {
    private val api = ApiInstance.apiInstance

    suspend fun getPreguntas(idtipotest: Int): Result<List<Question>>{
        return try{
            val response= api.getPreguntas(idtipotest)
            if( response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error al cargar preguntas: ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getRespuestas(idtipotest: Int): Result<List<Answer>>{
        return try{
            val response = api.getRespuestas(idtipotest)
            if( response.isSuccessful){
                Result.success(response.body()!!)
            }else{
                Result.failure(Exception("Error al cargar respuestas: ${response.message()}"))
            }
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    suspend fun createTest(testRequest: TestRespuesta): Result<Unit> {
        return try {
            val response = api.createTest(testRequest)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error al crear el test: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun asignarTestPuntaje(testRequest: TestPuntaje): Result<Unit>{
        return try{
            val response = api.asignarTestPuntaje(testRequest)
            if(response.isSuccessful){
                Result.success(Unit)
            }else{
                Result.failure(Exception("Error al actualizar test puntaje: ${response.code()} ${response.message()}"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }

    }

}