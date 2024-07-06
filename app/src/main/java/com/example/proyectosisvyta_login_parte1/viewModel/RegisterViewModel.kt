package com.example.proyectosisvyta_login_parte1.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectosisvyta_login_parte1.data.domain.LoginUsuarioUseCase
import com.example.proyectosisvyta_login_parte1.data.domain.RegistroUsuarioUseCase
import com.example.proyectosisvyta_login_parte1.data.model.RegistroUsuario
import com.example.proyectosisvyta_login_parte1.data.repository.RegistroUsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class RegisterViewModel : ViewModel() {
    private val repository = RegistroUsuarioRepository()
    private val registroUseCase = RegistroUsuarioUseCase(repository)

    private val _nombre = MutableStateFlow("")
    val nombre: StateFlow<String> = _nombre.asStateFlow()

    private val _apellido = MutableStateFlow("")
    val apellido: StateFlow<String> = _apellido.asStateFlow()

    private val _ubigeo = MutableStateFlow("")
    val ubigeo: StateFlow<String> = _ubigeo.asStateFlow()

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _telefono = MutableStateFlow("")
    val telefono: StateFlow<String> = _telefono.asStateFlow()

    private val _usuario = MutableStateFlow("")
    val usuario: StateFlow<String> = _usuario.asStateFlow()

    private val _contrasena = MutableStateFlow("")
    val contrasena: StateFlow<String> = _contrasena.asStateFlow()

    private val _confirmarContrasena = MutableStateFlow("")
    val confirmarContrasena: StateFlow<String> = _confirmarContrasena.asStateFlow()

    private val _genero = MutableStateFlow("")
    val genero: StateFlow<String> = _genero.asStateFlow()

    private val _fechaNacimiento = MutableStateFlow("")
    val fechaNacimiento: StateFlow<String> = _fechaNacimiento.asStateFlow()

    private val _displayFechaNacimiento = MutableStateFlow("")
    val displayFechaNacimiento: StateFlow<String> = _displayFechaNacimiento.asStateFlow()

    private val _showDatePicker = MutableStateFlow(false)
    val showDatePicker: StateFlow<Boolean> = _showDatePicker.asStateFlow()

    private val _registroExitoso = MutableStateFlow(false)
    val registroExitoso: StateFlow<Boolean> = _registroExitoso.asStateFlow()

    private val _errorRegistro = MutableStateFlow<String?>(null)
    val errorRegistro: StateFlow<String?> = _errorRegistro.asStateFlow()


    fun onNombreChanged(newNombre: String){
        _nombre.value = newNombre
    }

    fun onApellidoChanged(newApellido: String){
        _apellido.value = newApellido
    }

    fun onUbigeoChanged(newUbigeo: String){
        _ubigeo.value = newUbigeo
    }

    fun onEmailChanged(newEmail: String){
        _email.value = newEmail
    }

    fun onTelefonoChanged(newTelefono: String){
        _telefono.value = newTelefono
    }

    fun onUsuarioChanged(newUsuario: String){
        _usuario.value = newUsuario
    }

    fun onContrasenaChanged(newContrasena: String){
        _contrasena.value = newContrasena
    }

    fun onConfirmarContrasenaChanged(newConfirmarContrasena: String){
        _confirmarContrasena.value = newConfirmarContrasena
    }

    fun onGeneroChanged(newGenero: String){
        _genero.value = newGenero
    }

    fun onFechaNacimientoChanged(dateToSend: String, dateToDisplay: String){
        _fechaNacimiento.value = dateToSend
        _displayFechaNacimiento.value = dateToDisplay
    }

    fun showDatePickerDialog(show: Boolean){
        _showDatePicker.value = show
    }

    fun onRegisterClicked(){
        Log.d("RegisterViewModel", "Datos de registro: nombre=${_nombre.value}, apellidos=${_apellido.value}, ubigeo=${_ubigeo.value}, email=${_email.value}, telefono=${_telefono.value}, genero=${_genero.value}, fechaNacimiento=${_fechaNacimiento.value}, nickUsuario=${_usuario.value}, contrasena=${_contrasena.value}")

        if(_contrasena.value != _confirmarContrasena.value){
            _errorRegistro.value = "Las contraseñas deben coincidir"
            return
        }

        try {
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(_fechaNacimiento.value)
        } catch (e: ParseException) {
            _errorRegistro.value = "Formato de fecha incorrecto"
            return
        }

        val registroUsuario = RegistroUsuario(
            _nombre.value,
            _apellido.value,
            _ubigeo.value.toInt(),
            _email.value,
            _telefono.value,
            _genero.value,
            _fechaNacimiento.value,
            _usuario.value,
            _contrasena.value
        )


        viewModelScope.launch {
            val result = registroUseCase.registerUsuarioUC(registroUsuario)

            if(result.isSuccess){
                _registroExitoso.value = true
                _errorRegistro.value = null
                Log.d("RegisterViewModel", "RegistroExitoso")
            }else{
                _registroExitoso.value = false
                _errorRegistro.value = result.exceptionOrNull()?.message ?: "Error desconocido"
                Log.e("RegisterViewModel","Error en el registro: ${_errorRegistro.value}")
            }
        }
    }

}