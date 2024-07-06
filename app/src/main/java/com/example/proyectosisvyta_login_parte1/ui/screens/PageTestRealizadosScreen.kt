package com.example.proyectosisvyta_login_parte1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit

import androidx.compose.material3.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.components.TextFieldOwn
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import androidx.compose.runtime.*

data class TestRealizado(
    val tipoTest: String,
    val usuario: String,
    val fecha: String,
    var puntaje: Int,
    val nivel: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageTestRealizadosScreen(
    modifier: Modifier = Modifier
) {
    val nombresAlumnos = listOf("Maria Lopez", "Jose Perez", "Ana Martinez")
    val tiposTest = listOf("Test 1", "Test 2", "Test 3")

    var nombreAlumno by remember { mutableStateOf("") }
    var fechaTest by remember { mutableStateOf("") }
    var tipoTest by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }

    var expandedNombre by remember { mutableStateOf(false) }
    var expandedTipoTest by remember { mutableStateOf(false) }

    val testRealizados = remember {
        mutableStateListOf(
            TestRealizado("1", "Maria Lopez", "2024-06-21", 44, "grave"),
            TestRealizado("2", "Maria Lopez", "2024-06-21", 46, "grave"),
            TestRealizado("3", "Maria Lopez", "2024-06-21", 46, "grave")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFB8E986))  // Fondo verde claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ExposedDropdownMenuBox(
            expanded = expandedNombre,
            onExpandedChange = { expandedNombre = !expandedNombre },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            TextField(
                value = nombreAlumno,
                onValueChange = { nombreAlumno = it },
                readOnly = true,
                label = { Text("Nombre de Alumno") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedNombre) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedNombre,
                onDismissRequest = { expandedNombre = false }
            ) {
                nombresAlumnos.forEach { alumno ->
                    DropdownMenuItem(
                        text = { Text(alumno) },
                        onClick = {
                            nombreAlumno = alumno
                            expandedNombre = false
                        }
                    )
                }
            }
        }

        TextFieldOwn(
            label = R.string.fecha_test,
            value = fechaTest,
            onValueChanged = { fechaTest = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expandedTipoTest,
            onExpandedChange = { expandedTipoTest = !expandedTipoTest },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            TextField(
                value = tipoTest,
                onValueChange = { tipoTest = it },
                readOnly = true,
                label = { Text("Tipo de Test") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedTipoTest) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expandedTipoTest,
                onDismissRequest = { expandedTipoTest = false }
            ) {
                tiposTest.forEach { test ->
                    DropdownMenuItem(
                        text = { Text(test) },
                        onClick = {
                            tipoTest = test
                            expandedTipoTest = false
                        }
                    )
                }
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Test", modifier = Modifier.weight(1f))
                    Text("Usuario", modifier = Modifier.weight(2f))
                    Text("Fecha", modifier = Modifier.weight(2f))
                    Text("Puntaje", modifier = Modifier.weight(1f))
                    Text("Nivel", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(0.5f)) // Espacio para el botón
                }
            }
            items(testRealizados) { test ->
                var isEditing by remember { mutableStateOf(false) }
                var newPuntaje by remember { mutableStateOf(test.puntaje.toString()) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(test.tipoTest, modifier = Modifier.weight(1f))
                    Text(test.usuario, modifier = Modifier.weight(2f))
                    Text(test.fecha, modifier = Modifier.weight(2f))
                    if (isEditing) {
                        TextField(
                            value = newPuntaje,
                            onValueChange = { newPuntaje = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    } else {
                        Text(test.puntaje.toString(), modifier = Modifier.weight(1f))
                    }
                    Text(test.nivel, color = Color.Red, modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            if (isEditing) {
                                test.puntaje = newPuntaje.toIntOrNull() ?: test.puntaje
                            }
                            isEditing = !isEditing
                        },
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Editar puntaje")
                    }
                }
            }
        }

        Button(
            onClick = { /* Acción de aceptar cambios */ },
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text("Aceptar Cambios")
        }

        Text(
            text = "Observaciones",
            fontSize = 18.sp,
            color = Color.Black,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.Start)
        )

        // Observaciones como un "TextArea"
        TextField(
            value = observaciones,
            onValueChange = { observaciones = it },
            label = { Text("Ingrese Observaciones") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Default
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(top = 8.dp),
            maxLines = 10 // Permitir hasta 10 líneas de texto
        )

        Button(
            onClick = { /* Acción de enviar observaciones */ },
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text("Enviar Observaciones")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageTestRealizadosPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageTestRealizadosScreen()
    }
}