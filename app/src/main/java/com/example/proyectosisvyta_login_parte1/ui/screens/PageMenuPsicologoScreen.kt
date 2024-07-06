package com.example.proyectosisvyta_login_parte1.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import com.example.proyectosisvyta_login_parte1.viewModel.LoginViewModel
import kotlin.math.log

@Composable
fun PageMenuPsicologoScreen(
    onMapaCalorClicked: () -> Unit,
    onCloseSessionButtonClicked: () -> Unit,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier

){
    val usuario by loginViewModel.usuario.collectAsState()
    val idusuario by loginViewModel.idusuario.collectAsState()

    Log.d("PageMenuPsicologoScreen", "Usuario en menu: $usuario")


    Column(
        modifier = Modifier
        //    .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        //verticalArrangement = Arrangement.Center
    ){
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "Bienvenido, $usuario",
            fontSize = 24.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ){
            Text(stringResource(R.string.Perfil))

        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ){
            Text(stringResource(R.string.Lista_Pacientes))
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)

        ){
            Text(stringResource(R.string.Test_Realizados))
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = onMapaCalorClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ){
            Text(stringResource(R.string.Mapa))
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ){
            Text(stringResource(R.string.Realizar_Cita))
        }

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            onClick = onCloseSessionButtonClicked,
            modifier = Modifier
                .height(48.dp)
                .align(Alignment.End)
                .padding(horizontal = 24.dp),
                    /*colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Red,

                    )*/
        ){
            Text(stringResource(R.string.Cerrar_Sesion))
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PageMenuPsicologoPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageMenuPsicologoScreen(
            onMapaCalorClicked = {},
            onCloseSessionButtonClicked = {},
            loginViewModel = LoginViewModel(),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
        )

    }
}