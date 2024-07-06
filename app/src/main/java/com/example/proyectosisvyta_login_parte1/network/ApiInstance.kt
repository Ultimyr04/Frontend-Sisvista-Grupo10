package com.example.proyectosisvyta_login_parte1.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiInstance {

    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl("https://backendsisvista-grupo10.onrender.com") //link del backend en render
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiInstance by lazy{
        retrofit.create(ApiService::class.java)
    }
}