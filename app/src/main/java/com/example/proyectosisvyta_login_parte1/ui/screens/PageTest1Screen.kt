package com.example.proyectosisvyta_login_parte1.ui.screens


import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.data.model.Question

import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import com.example.proyectosisvyta_login_parte1.viewModel.TestViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Respuesta
import com.example.proyectosisvyta_login_parte1.ui.components.QuestionItem

import com.example.proyectosisvyta_login_parte1.viewModel.LoginViewModel


@Composable
fun PageTest1Screen(
    onCancelButtonClicked: () ->Unit,
    onTestSubmitted: () -> Unit,
    testViewModel: TestViewModel = viewModel(),
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
){
    LaunchedEffect(Unit) {
        testViewModel.loadPreguntasRespuestas(1)
    }

    val textPreguntas by testViewModel.textPreguntas.collectAsState()
    val textRespuestas by testViewModel.textRespuestas.collectAsState()


    val respuestas by testViewModel.respuestas.collectAsState()
    val testExitoso by testViewModel.testExitoso.collectAsState()
    val errorTest by testViewModel.errorTest.collectAsState()
    val totalScore by testViewModel.totalScore.collectAsState()
    val ansiedadLevel by testViewModel.ansiedadLevel.collectAsState()
    val idusuario by loginViewModel.idusuario.collectAsState()

    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showResultDialog by remember { mutableStateOf(false) }
    var showIncompleteAnswersDialog by remember { mutableStateOf(false) }

    Log.d("PageTestScreen1", "IDUsuario en test: $idusuario")

    Column{
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(textPreguntas.size){ index ->
                val textpregunta = textPreguntas[index]
                val respuesta = respuestas.getOrNull(index)?.respuesta ?: -1
                QuestionItem(
                    question = textpregunta,
                    answer = respuesta,
                    textRespuestas= textRespuestas,
                    onAnswerChanged = { newAnswer ->
                        testViewModel.onRespuestaChanged(index, newAnswer)
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ){
            OutlinedButton(
                onClick = onCancelButtonClicked
            ){
                Text(stringResource(R.string.Cancelar).uppercase())
            }

            Spacer(modifier = Modifier.width(15.dp))

            Button(
                onClick = {
                    if(respuestas.size == textPreguntas.size && respuestas.none{ it.respuesta == -1}){
                        showConfirmationDialog = true
                    }else{
                        showIncompleteAnswersDialog = true
                    }
                }
            ){
                Text(stringResource(R.string.Enviar).uppercase())
            }
        }
    }

    if(showIncompleteAnswersDialog){
        AlertDialog(
            onDismissRequest = { showIncompleteAnswersDialog = false },
            title = { Text(text = "Respuestas Incompletas")},
            text = { Text(text = "Responder todas las preguntas, por favor")},
            confirmButton = {
                Button(
                    onClick = {
                        showIncompleteAnswersDialog = false
                    }
                ){
                    Text(text = "OK")
                }
            }
        )
    }


    if(showConfirmationDialog){
        AlertDialog(
            onDismissRequest = { showConfirmationDialog = false },
            title = { Text(text = "Confirmación de Test")},
            text = { Text(text = "¿Está seguro de sus respuestas?")},
            confirmButton = {
                Button(
                    onClick = {
                        showConfirmationDialog = false
                        idusuario?.let {
                            Log.d("PageTestScreen", "Enviando test para idusuario: $it")
                            testViewModel.onTestSubmit(idusuario = it, idtipotest=1)
                            testViewModel.asignarTestPuntaje(idusuario = it, idtipotest = 1)
                        }
                        showResultDialog = true

                    }
                ){
                    Text(text = "Si")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showConfirmationDialog = false }
                ) {
                    Text(text = "No")
                }
            }
        )
    }

    if(showResultDialog){
        AlertDialog(
            onDismissRequest = { showResultDialog = false },
            title = { Text(text = "Resultados del Test")},
            text = {
                if(testExitoso){
                    Text(text = "TEST REGISTRADO CORRECTAMENTE")
                }else {
                    Text(text = "ERROR AL REGISTRAR TEST")
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showResultDialog = false
                        onTestSubmitted()
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }

    if(testExitoso){
        Log.d("PageTest1Screen", "Test enviado correctamente")
    }else if(errorTest != null){
        Log.e("PageTest1Screen", "Error al enviar el test: $errorTest")
    }

}





@Preview(showBackground = true)
@Composable
fun PageTestPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageTest1Screen(
            onCancelButtonClicked = {},
            onTestSubmitted = {},
            loginViewModel = LoginViewModel(),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .verticalScroll(rememberScrollState())
        )

    }
}
