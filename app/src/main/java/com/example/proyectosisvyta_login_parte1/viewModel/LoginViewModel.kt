package com.example.proyectosisvyta_login_parte1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectosisvyta_login_parte1.data.domain.LoginUsuarioUseCase
import com.example.proyectosisvyta_login_parte1.data.repository.LoginUsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel(){
    private val repository = LoginUsuarioRepository()
    private val usuarioUseCase = LoginUsuarioUseCase(repository)

    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario.asStateFlow()

    private val _contrasena = MutableStateFlow("")
    val contrasena: StateFlow<String> = _contrasena.asStateFlow()

    private val _idusuario = MutableStateFlow<Int?>(null)
    val idusuario: StateFlow<Int?> = _idusuario.asStateFlow()

    private val _rol = MutableStateFlow("")
    val rol: StateFlow<String> = _rol.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso.asStateFlow()

    private val _errorLogin = MutableStateFlow<String?>(null)
    val errorLogin: StateFlow<String?> = _errorLogin.asStateFlow()


    fun onUsuarioChanged(newUsuario: String){
        _usuario.value = newUsuario
    }

    fun onContrasenaChanged(newContrasena: String){
        _contrasena.value = newContrasena
    }

    fun onLoginClicked(onLoginSuccess: (String) -> Unit){
        if(_usuario.value.isEmpty()){
            _errorLogin.value = "Usuario vacio"
            return
        }

        if(_contrasena.value.isEmpty()){
            _errorLogin.value = "Contrasena vacia"
            return
        }

        viewModelScope.launch{
            val result = usuarioUseCase.loginUsuarioUC(_usuario.value,_contrasena.value)
            if(result.isSuccess){
                val usuario = result.getOrNull()
                _idusuario.value = usuario?.idusuario
                _rol.value = usuario?.rol ?: ""
                _loginExitoso.value = true
                _errorLogin.value = null
                Log.d("LoginViewModel","Login exitoso, usuario: ${_usuario.value}, idusuario: ${_idusuario.value}, rol: ${_rol.value}")
                onLoginSuccess(_rol.value)
            } else{
                _loginExitoso.value = false
                _errorLogin.value = result.exceptionOrNull()?.message ?: "error desconocido"
                Log.e("LoginViewModel","Error en el login: ${_errorLogin.value}")
            }
        }
    }
}