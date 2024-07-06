package com.example.proyectosisvyta_login_parte1.data.model

data class TestRealizado(
    val tipoTest: Int,
    val usuario: String,
    val fecha: String,
    var puntaje: Int,
    val nivel: String
)
