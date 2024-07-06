package com.example.proyectosisvyta_login_parte1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectosisvyta_login_parte1.data.domain.TestUseCase
import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Question
import com.example.proyectosisvyta_login_parte1.data.model.Respuesta
import com.example.proyectosisvyta_login_parte1.data.model.TestPuntaje
import com.example.proyectosisvyta_login_parte1.data.model.TestRespuesta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {
    private val testUseCase = TestUseCase()

    private val _textPreguntas = MutableStateFlow<List<Question>>(emptyList())
    val textPreguntas: StateFlow<List<Question>> = _textPreguntas

    private val _textRespuestas = MutableStateFlow<List<Answer>>(emptyList())
    val textRespuestas: StateFlow<List<Answer>> = _textRespuestas

    private val _respuestas = MutableStateFlow<List<Respuesta>>(emptyList())
    val respuestas: StateFlow<List<Respuesta>> = _respuestas.asStateFlow()

    private val _testExitoso = MutableStateFlow(false)
    val testExitoso: StateFlow<Boolean> = _testExitoso.asStateFlow()

    private val _testPuntajeExitoso = MutableStateFlow(false)
    val testPuntajeExitoso: StateFlow<Boolean> = _testPuntajeExitoso.asStateFlow()

    private val _errorTestPuntaje = MutableStateFlow<String?>(null)
    val errorTestPuntaje: StateFlow<String?> = _errorTestPuntaje.asStateFlow()

    private val _errorTest = MutableStateFlow<String?>(null)
    val errorTest: StateFlow<String?> = _errorTest.asStateFlow()

    private val _totalScore = MutableStateFlow(0)
    val totalScore: StateFlow<Int> = _totalScore.asStateFlow()

    private val _ansiedadLevel = MutableStateFlow("")
    val ansiedadLevel: StateFlow<String> = _ansiedadLevel.asStateFlow()

    fun onRespuestaChanged(index: Int, newRespuesta: Int) {
        val newRespuestas = _respuestas.value.toMutableList()
        if(index<newRespuestas.size){
            newRespuestas[index] = Respuesta(index + 1, newRespuesta)
        }else{
            while (newRespuestas.size <= index){
                newRespuestas.add(Respuesta(newRespuestas.size + 1,1))
            }
            newRespuestas[index] = Respuesta(index+1,newRespuesta)
        }

        _respuestas.value = newRespuestas
    }

    fun loadPreguntasRespuestas(idtipotest: Int){
        viewModelScope.launch {
            val resultPreguntas = testUseCase.getPreguntas(idtipotest)
            val resultRespuestas = testUseCase.getRespuestas(1)

            resultPreguntas.onSuccess { preguntasCargadas ->
                _textPreguntas.value = preguntasCargadas.filterNotNull()
                Log.d("TestViewModel","Preguntas cargadas: ${_textPreguntas.value.map { it.textopregunta }}")
            }.onFailure {
                Log.e("TestViewModel","Error al cargar preguntas: ${it.message}")
                _errorTest.value = "Error al cargar preguntas"
            }

            resultRespuestas.onSuccess { respuestasCargadas ->
                _textRespuestas.value = respuestasCargadas.filterNotNull()
                Log.d("TestViewModel","Respuestas cargadas: ${_textRespuestas.value.map { it.textorespuesta }}")
            }.onFailure {
                Log.e("TestViewModel","Error al cargar respuestas: ${it.message}")
                _errorTest.value = "Error al cargar respuestas"
            }
        }
    }

    fun onTestSubmit(idusuario: Int, idtipotest: Int) {
        viewModelScope.launch {
            val testRequest = TestRespuesta(
                idusuario = idusuario,
                idtipotest = idtipotest,
                respuestas = _respuestas.value
            )
            Log.d("TestViewModel", "Enviando testresquest: $testRequest")
            val result = testUseCase.createTest(testRequest)
            if (result.isSuccess) {
                _totalScore.value = _respuestas.value.sumOf { it.respuesta }
                _ansiedadLevel.value = when (_totalScore.value) {
                    in 10..19 -> "Nivel de ansiedad mínimo"
                    in 20..29 -> "Nivel de ansiedad leve"
                    in 30..39 -> "Nivel de ansiedad moderado"
                    in 40..50 -> "Nivel de ansiedad severo"
                    else -> "Resultado no válido"
                }

                _testExitoso.value = true
                _errorTest.value = null
                Log.d("TestViewModel","Test enviado correctamente")

            } else {
                _testExitoso.value = false
                _errorTest.value = result.exceptionOrNull()?.message ?: "Error desconocido al crear el test"
                Log.e("TestViewModel","Error al enviar el test: ${_errorTest.value}")
            }
        }
    }


    fun asignarTestPuntaje(idusuario: Int, idtipotest: Int){
        viewModelScope.launch {
            val testRequest = TestPuntaje(
                idusuario = idusuario,
                idtipotest = idtipotest
            )
            val result = testUseCase.asignarTestPuntaje(testRequest)
            if(result.isSuccess){
                _testPuntajeExitoso.value = true
                _errorTestPuntaje.value = null
                Log.d("TestViewModel","Test Puntaje actualizado correctamente")
            }else{
                _testPuntajeExitoso.value = false
                _errorTestPuntaje.value = result.exceptionOrNull()?.message ?: "Error desconocido al actualizar testPuntaje"
                Log.e("TestViewModel","Error al actualizar testPuntaje: ${_errorTestPuntaje.value}")
            }
        }
    }

}