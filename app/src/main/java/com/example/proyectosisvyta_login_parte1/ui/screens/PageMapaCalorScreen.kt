package com.example.proyectosisvyta_login_parte1.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.proyectosisvyta_login_parte1.R
import com.example.proyectosisvyta_login_parte1.ui.theme.ProyectoSisvytaLoginParte1Theme
import com.example.proyectosisvyta_login_parte1.viewModel.MapaCalorViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.TileOverlayOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.heatmaps.WeightedLatLng
import com.google.maps.android.compose.MapsComposeExperimentalApi

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun PageMapaCalorScreen(
    modifier : Modifier = Modifier,
    onAcceptClicked: () -> Unit,
    mapaCalorViewModel: MapaCalorViewModel = viewModel()
){
    val context = LocalContext.current
    val peruCenter = LatLng(-9.19, -75.0152) //Centro del perú
    val cameraPositionState = rememberCameraPositionState{
        position = CameraPosition.fromLatLngZoom(peruCenter,5f)
    }

    val mapaCalor by mapaCalorViewModel.mapaCalor.collectAsState()
    val isLoading by mapaCalorViewModel.isLoading.collectAsState()
    val errorMessage by mapaCalorViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
       mapaCalorViewModel.loadMapaCalor()
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "MAPA DEL PERU")

        Spacer(modifier = Modifier.height(16.dp))

        if(isLoading){
            Text("Cargando datos...")
        }else if(errorMessage != null){
            Text(errorMessage!!)
        }else{
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                cameraPositionState = cameraPositionState,
                uiSettings = MapUiSettings(zoomControlsEnabled = false)
            ){
                val heatmapData = mutableListOf<WeightedLatLng>()

                mapaCalor.forEach { data ->
                    heatmapData.add(
                        WeightedLatLng(
                            LatLng(data.latitude.toDouble(), data.longitude.toDouble()),
                            data.promedio.toDouble()
                        )
                    )
                }

                if(heatmapData.isNotEmpty()){
                    val heatmapProvider = HeatmapTileProvider.Builder()
                        .weightedData(heatmapData)
                        .build()

                    com.google.maps.android.compose.TileOverlay(tileProvider = heatmapProvider)

                }

            }
        }

        Spacer(modifier = Modifier.height(30.dp))
        
        Button(
            onClick = onAcceptClicked,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Aceptar")
        }



    }
}

@Preview(showBackground = true)
@Composable
fun PageMapaCalorPreview() {
    ProyectoSisvytaLoginParte1Theme {
        PageMapaCalorScreen(
            //onLoginButtonClicked = {},
            //onRegisterTextClicked = {},
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_medium))
                .fillMaxSize(),
            onAcceptClicked = {},
            mapaCalorViewModel = MapaCalorViewModel()
        )
    }
}