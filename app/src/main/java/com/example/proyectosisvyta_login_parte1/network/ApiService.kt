package com.example.proyectosisvyta_login_parte1.network

import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Estudiante
import com.example.proyectosisvyta_login_parte1.data.model.MapaCalor
import com.example.proyectosisvyta_login_parte1.data.model.Question
import com.example.proyectosisvyta_login_parte1.data.model.RegistroUsuario
import com.example.proyectosisvyta_login_parte1.data.model.TestPuntaje
import com.example.proyectosisvyta_login_parte1.data.model.TestRealizado
import com.example.proyectosisvyta_login_parte1.data.model.TestRespuesta
import com.example.proyectosisvyta_login_parte1.data.model.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("/api/login")
    suspend fun loginUsuarioApiService(@Body loginRequest: Map<String, String>) : Response<Usuario>

    @POST("/api/register")
    suspend fun registerUsuarioApiService(@Body registerRequest: RegistroUsuario ): Response<RegistroUsuario>

    @GET("/api/formulario/preguntas")
    suspend fun getPreguntas(@Query("idtipotest") idtipotest: Int): Response<List<Question>>

    @GET("/api/formulario/respuestas")
    suspend fun getRespuestas(@Query("idtipotest") idtipotest: Int): Response<List<Answer>>

    @POST("/api/test_respuesta_routes/test")
    suspend fun createTest(@Body testRequest: TestRespuesta): Response<Void>

    @POST("/api/test_puntaje")
    suspend fun asignarTestPuntaje(@Body testRequest: TestPuntaje): Response<Void>


    @GET("/api/testrealizados/estudiantes")
    suspend fun getEstudiantes(): Response<List<Estudiante>>

    @GET("/api/testrealizados/{idusuario}")
    suspend fun getTestsPorUsuario(@Path("idusuario") idusuario: Int): Response<List<TestRealizado>>

    @POST("/api/testrealizado/actualizarTest/{id_test}")
    suspend fun actualizarTest(@Path("id_test") idTest: Int, @Body puntaje: Map<String, Int>): Response<Map<String, String>>



    @GET("/api/mapa_calor")
    suspend fun getMapaCalor(): Response<List<MapaCalor>>
}