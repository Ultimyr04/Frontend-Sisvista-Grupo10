package com.example.proyectosisvyta_login_parte1.ui.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectosisvyta_login_parte1.data.model.Answer
import com.example.proyectosisvyta_login_parte1.data.model.Question

@Composable
fun QuestionItem(
    question: Question?,
    answer : Int,
    textRespuestas: List<Answer>,
    onAnswerChanged : (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    if(question==null){
        Log.d("QuestionItem","Pregunta recibida es nula")
        return
    }

    val numeroPregunta = when(question.idtipotest){
        1 -> question.idpregunta
        2 -> question.idpregunta - 10
        3 -> question.idpregunta - 20
        else -> question.idpregunta
    }


    Card(
        modifier = Modifier
            .padding(15.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = numeroPregunta.toString(),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(end = 10.dp)
                    )
                }

                Text(
                    text = question.textopregunta ?: "Texto no disponible",
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .fillMaxWidth(),
                    fontSize = 22f.sp
                )
            }

            textRespuestas.forEach { respuesta ->
                OptionsAnswer(
                    textAnswer = respuesta.textorespuesta,
                    isSelected = respuesta.idrespuesta == answer,
                    onClick = { onAnswerChanged(respuesta.idrespuesta) }
                )

            }
        }
    }
}