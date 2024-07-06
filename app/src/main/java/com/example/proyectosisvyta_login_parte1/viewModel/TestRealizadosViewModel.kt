package com.example.proyectosisvyta_login_parte1.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectosisvyta_login_parte1.data.domain.TestRealizadosUseCase
import com.example.proyectosisvyta_login_parte1.data.model.Estudiante
import com.example.proyectosisvyta_login_parte1.data.model.TestRealizado
import kotlinx.coroutines.launch

class TestRealizadosViewModel : ViewModel() {
    private val testRealizadosUseCase = TestRealizadosUseCase()

    var estudiantes = mutableStateListOf<Estudiante>()
    var tests = mutableStateListOf<TestRealizado>()

    fun getEstudiantes() {
        viewModelScope.launch {
            val result = testRealizadosUseCase.getEstudiantes()

            result.onSuccess {
                estudiantes.clear()
                estudiantes.addAll(it)
            }.onFailure {
                Log.e("testRealizadosViewModel", "Error fetching estudiantes", it)
            }
        }
    }

    fun getTestsPorUsuario(idUsuario: Int){
        viewModelScope.launch {
            val result = testRealizadosUseCase.getTestsPorUsuario(idUsuario).onSuccess {
                tests.clear()
                tests.addAll(it)
            }.onFailure {
                Log.e("TestViewModel", "Error fetching tests", it)
            }
        }
    }

    fun actualizarPuntajeTest(idTest: Int, puntaje: Int){
        viewModelScope.launch {
            val result = testRealizadosUseCase.actualizarPuntajeTest(idTest,puntaje).onSuccess {
                Log.d("TestViewModel", "Puntaje actualizado")

            }.onFailure {
                Log.e("TestViewModel", "Error updating test", it)
            }
        }
    }

}