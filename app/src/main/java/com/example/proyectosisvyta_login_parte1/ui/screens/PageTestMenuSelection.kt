package com.example.proyectosisvyta_login_parte1.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme

@Composable
fun PageTestMenuSelection(
    onCancelButtonClicked: () ->Unit,
    onTest1Clicked: () -> Unit,
    onTest2Clicked: () -> Unit,
    onTest3Clicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.padding(16.dp))


        Button(
            onClick = onTest1Clicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ) {
            Text("Test 1")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onTest2Clicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ) {
            Text("Test 2")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            onClick = onTest3Clicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp)
        ) {
            Text("Test 3")
        }

        Spacer(modifier = Modifier.padding(16.dp))

        OutlinedButton(
            onClick = onCancelButtonClicked
        ){
            Text(stringResource(R.string.Cancelar).uppercase())
        }

    }
}

@Preview(showBackground = true)
@Composable
fun PageTestSelectionPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageTestMenuSelection(
            onCancelButtonClicked = {},
            onTest1Clicked = {},
            onTest2Clicked = {},
            onTest3Clicked = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}