package com.example.proyectosisvyta_login_parte1.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.components.TextFieldOwn
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import com.example.proyectosisvyta_login_parte1.viewModel.RegisterViewModel
import java.util.Calendar

@Composable
fun PageRegisterScreen(
    onRegisterButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    val nombre by registerViewModel.nombre.collectAsState()
    val apellido by registerViewModel.apellido.collectAsState()
    val ubigeo by registerViewModel.ubigeo.collectAsState()
    val email by registerViewModel.email.collectAsState()
    val telefono by registerViewModel.telefono.collectAsState()
    val usuario by registerViewModel.usuario.collectAsState()
    val contrasena by registerViewModel.contrasena.collectAsState()
    val confirmarContrasena by registerViewModel.confirmarContrasena.collectAsState()
    val genero by registerViewModel.genero.collectAsState()
    val fechaNacimiento by registerViewModel.fechaNacimiento.collectAsState()
    val showDatePicker by registerViewModel.showDatePicker.collectAsState()

    val displayFechaNacimiento by registerViewModel.displayFechaNacimiento.collectAsState()


    val registroExistoso by registerViewModel.registroExitoso.collectAsState()
    val errorRegistro by registerViewModel.errorRegistro.collectAsState()

    val isFormValid = nombre.isNotBlank() && apellido.isNotBlank() && ubigeo.isNotBlank() &&
                      email.isNotBlank() && telefono.isNotBlank() && contrasena.isNotBlank() &&
                      confirmarContrasena.isNotBlank() && genero.isNotBlank() && fechaNacimiento.isNotBlank()

    val context = LocalContext.current

    if (showDatePicker) {
        ShowDatePickerDialog(context) { dateToSend, dateToDisplay ->
            registerViewModel.onFechaNacimientoChanged(dateToSend, dateToDisplay)
            registerViewModel.showDatePickerDialog(false)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextFieldOwn(
            label = R.string.Nombre_Registro,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = nombre,
            onValueChanged = { registerViewModel.onNombreChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.Apellido_Registro,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = apellido,
            onValueChanged = { registerViewModel.onApellidoChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.Ubigeo_Registro,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = ubigeo,
            onValueChanged = { registerViewModel.onUbigeoChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.Email_Registro,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
            ),
            value = email,
            onValueChanged = { registerViewModel.onEmailChanged(it)},
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.Telefono_Registro,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            value = telefono,
            onValueChanged = { registerViewModel.onTelefonoChanged(it)},
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        Row(
            modifier = Modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = stringResource(R.string.Genero)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                RadioButton(
                    selected = genero == "FEMENINO" ,
                    onClick = { registerViewModel.onGeneroChanged("FEMENINO") }
                )
                Text(text = stringResource(R.string.Femenino))
            }

            Spacer(modifier = Modifier.width(2.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                RadioButton(
                    selected = genero == "MASCULINO",
                    onClick = { registerViewModel.onGeneroChanged("MASCULINO") }
                )
                Text(text = stringResource(R.string.Masculino))
            }

        }

        Row(
            modifier = Modifier
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Text(
                text = stringResource(R.string.Fecha_Nacimiento),
                fontSize = 13.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(start = 5.dp)

            )

            Spacer(modifier = Modifier.width(4.dp))

            OutlinedButton(
                onClick = { registerViewModel.showDatePickerDialog(true) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)

            ){
                Text(text = if(displayFechaNacimiento.isNotEmpty()) displayFechaNacimiento else "Seleccionar Fecha")
            }
        }

        TextFieldOwn(
            label = R.string.usuario,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            value = usuario ,
            onValueChanged = { registerViewModel.onUsuarioChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.contrasena_login,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            value = contrasena,
            onValueChanged = { registerViewModel.onContrasenaChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        TextFieldOwn(
            label = R.string.confirmar_contrasena,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            value = confirmarContrasena,
            onValueChanged = { registerViewModel.onConfirmarContrasenaChanged(it) },
            modifier = Modifier
                .padding(bottom = 5.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedButton(
                onClick = onCancelButtonClicked
            ) {
                Text(stringResource(R.string.Cancelar).uppercase())
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    registerViewModel.onRegisterClicked()
                },
                enabled = isFormValid
            ) {
                Text(stringResource(R.string.Registrar).uppercase())
            }
        }

        LaunchedEffect(registroExistoso) {
            if(registroExistoso){
                Toast.makeText(context, "Registro Correcto", Toast.LENGTH_SHORT).show()
                onRegisterButtonClicked()
            }
        }

        LaunchedEffect(errorRegistro) {
            errorRegistro?.let{
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }


    }





}


@Composable
fun ShowDatePickerDialog(context: Context, onDateSelected: (String, String) -> Unit){
    val calendar = Calendar.getInstance()
    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val formattedMonth = (month+1).toString().padStart(2,'0')
            val formattedDay = dayOfMonth.toString().padStart(2,'0')
            val displayDate = "$formattedDay/$formattedMonth/$year"
            val sendDate = "$year-$formattedMonth-$formattedDay"
            onDateSelected(sendDate, displayDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}

@Preview(showBackground = true)
@Composable
fun PageRegisterPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageRegisterScreen(
            onRegisterButtonClicked = {},
            onCancelButtonClicked = {},
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
        )
    }
}