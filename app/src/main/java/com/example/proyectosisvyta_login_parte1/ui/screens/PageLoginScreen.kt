package com.example.proyectosisvyta_login_parte1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.components.TextFieldOwn
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import com.example.proyectosisvyta_login_parte1.viewModel.LoginViewModel

@Composable
fun PageLoginScreen(
    onLoginButtonClicked: (String) -> Unit,
    onRegisterTextClicked: () -> Unit,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
){
    val usuario by loginViewModel.usuario.collectAsState()
    val contrasena by loginViewModel.contrasena.collectAsState()
    val loginExitoso by loginViewModel.loginExitoso.collectAsState()
    val errorLogin by loginViewModel.errorLogin.collectAsState()

    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Image(
            painter = painterResource(id = R.drawable.imagenusuario),
            contentDescription = null,
            modifier = Modifier

        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        TextFieldOwn(
            label = R.string.usuario,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ) ,
            value = usuario,
            onValueChanged = { loginViewModel.onUsuarioChanged(it) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
        )

        TextFieldOwn(
            label = R.string.contrasena_login,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            value = contrasena,
            onValueChanged = { loginViewModel.onContrasenaChanged(it) },
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth(),
            visualTransformation = if(passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if(passwordVisible) Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }){
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )

        Button(
            onClick =  {
                loginViewModel.onLoginClicked { rol ->
                    onLoginButtonClicked(rol)
                }
                    if(!loginExitoso){
                        Toast.makeText(context, errorLogin ?: "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier
        ){
            Text(stringResource(R.string.inicio_sesion))
        }

        ClickableText(
            text = AnnotatedString(stringResource(R.string.olvido_contrasena)),
            onClick = { },
            style = TextStyle(
                color = Color.Blue,
                fontSize = 13.sp
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = stringResource(R.string.Registrarse),
                modifier = Modifier
            )

            ClickableText(
                text = AnnotatedString(stringResource(R.string.Registrar)),
                onClick = {onRegisterTextClicked()},
                style = TextStyle(
                    color = Color.Blue,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageLoginPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageLoginScreen(
            onLoginButtonClicked = {},
            onRegisterTextClicked = {},
            loginViewModel = LoginViewModel(),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
        )
    }
}
