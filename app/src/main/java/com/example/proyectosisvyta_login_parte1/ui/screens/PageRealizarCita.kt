package com.example.proyectosisvyta_login_parte1.ui.screens


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme

data class CitaRealizada(
    val usuario: String,
    var nivel: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageRealizarCitaScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val citasRealizadas = remember {
        mutableStateListOf(
            CitaRealizada("Maria Lopez", "grave"),
            CitaRealizada("Jose Perez", "moderado"),
            CitaRealizada("Ana Martinez", "leve")
        )
    }

    val nombresAlumnos = listOf("Maria Lopez", "Jose Perez", "Ana Martinez")
    var nombreAlumno by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFB8E986))  // Fondo verde claro
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            TextField(
                value = nombreAlumno,
                onValueChange = { nombreAlumno = it },
                readOnly = true,
                label = { Text("Nombre de Alumno") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                nombresAlumnos.forEach { alumno ->
                    DropdownMenuItem(
                        text = { Text(alumno) },
                        onClick = {
                            nombreAlumno = alumno
                            expanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = { /* Acción de búsqueda */ },
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Text("Buscar")
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
                    Text("Usuario", modifier = Modifier.weight(2f))
                    Text("Nivel de Ansiedad", modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.weight(0.5f)) // Espacio para los botones
                }
            }
            items(citasRealizadas) { cita ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(cita.usuario, modifier = Modifier.weight(2f))
                    Text(cita.nivel, modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.weight(1f)) {
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse("https://wa.me/?text=Hola%20${cita.usuario},%20tu%20nivel%20de%20ansiedad%20es%20${cita.nivel}.")
                                }
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(Icons.Default.Send, contentDescription = "Enviar mensaje por WhatsApp")
                        }
                        IconButton(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:")
                                    putExtra(Intent.EXTRA_SUBJECT, "Nivel de Ansiedad")
                                    putExtra(Intent.EXTRA_TEXT, "Hola ${cita.usuario}, tu nivel de ansiedad es ${cita.nivel}.")
                                }
                                context.startActivity(intent)
                            }
                        ) {
                            Icon(Icons.Default.Email, contentDescription = "Enviar mensaje por correo electrónico")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PageRealizarCitaPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageRealizarCitaScreen()
    }
}