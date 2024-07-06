package com.example.proyectosisvyta_login_parte1.data.domain

import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Question
import com.example.proyectosisvyta_login_parte1.data.model.TestPuntaje
import com.example.proyectosisvyta_login_parte1.data.model.TestRespuesta
import com.example.proyectosisvyta_login_parte1.data.repository.TestRepository

class TestUseCase {
    private val repository = TestRepository()

    suspend fun createTest(testRequest: TestRespuesta): Result<Unit>{
        return repository.createTest(testRequest)
    }

    suspend fun getPreguntas(idtipotest: Int): Result<List<Question>>{
        return repository.getPreguntas(idtipotest)
    }

    suspend fun getRespuestas(idtipotest: Int): Result<List<Answer>>{
        return repository.getRespuestas(idtipotest)
    }

    suspend fun asignarTestPuntaje(testRequest: TestPuntaje): Result<Unit>{
        return repository.asignarTestPuntaje(testRequest)
    }

}