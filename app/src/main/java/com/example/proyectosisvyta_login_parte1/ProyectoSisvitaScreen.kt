package com.example.proyectosisvyta_login_parte1

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectosisvyta_login_parte1.ui.components.SisvitaTopAppBar
import com.example.proyectosisvyta_login_parte1.ui.screens.PageLoginScreen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageMapaCalorScreen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageMenuPacienteScreen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageMenuPsicologoScreen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageRegisterScreen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageTest1Screen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageTest2Screen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageTest3Screen
import com.example.proyectosisvyta_login_parte1.ui.screens.PageTestMenuSelection
import com.example.proyectosisvyta_login_parte1.viewModel.LoginViewModel


enum class ProyectoSisvitaScreen(@StringRes val title: Int) {
    Login(title = R.string.app_name),
    MenuPaciente(title = R.string.Menu_Paciente),
    MenuPsicologo(title = R.string.Menu_Psicologo),
    MapaCalor(title = R.string.MAPA_CALOR),
    Registro(title = R.string.registro),
    MenuTest(title = R.string.MENU_TEST),
    Test1(title = R.string.TEST1),
    Test2(title = R.string.TEST2),
    Test3(title = R.string.TEST3)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyectoSisvitaApp(loginViewModel: LoginViewModel) {
    val navController = rememberNavController()

    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = ProyectoSisvitaScreen.valueOf(
        backStackEntry?.destination?.route?.substringBefore("/") ?: ProyectoSisvitaScreen.Login.name
    )

    Scaffold(
        topBar = {
            SisvitaTopAppBar(
                currentScreenTitle = currentScreen.title,
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ProyectoSisvitaScreen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ProyectoSisvitaScreen.Login.name) {
                PageLoginScreen(
                    onLoginButtonClicked = { rol ->
                        when(rol){
                            "ESTUDIANTE" -> navController.navigate(ProyectoSisvitaScreen.MenuPaciente.name)
                            "PSICOLOGO" -> navController.navigate(ProyectoSisvitaScreen.MenuPsicologo.name)
                        }
                    },
                    onRegisterTextClicked = { navController.navigate(ProyectoSisvitaScreen.Registro.name) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            composable(route = ProyectoSisvitaScreen.Registro.name) {
                PageRegisterScreen(
                    onRegisterButtonClicked = { navController.navigate(ProyectoSisvitaScreen.Login.name) },
                    onCancelButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.Login.name, inclusive = false) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            composable(route = ProyectoSisvitaScreen.MenuPaciente.name) {
                PageMenuPacienteScreen(
                    onRealizarTest = { navController.navigate(ProyectoSisvitaScreen.MenuTest.name) },
                    onCloseSessionButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.Login.name, inclusive = false) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ProyectoSisvitaScreen.MenuPsicologo.name) {
                PageMenuPsicologoScreen(
                    onMapaCalorClicked = { navController.navigate(ProyectoSisvitaScreen.MapaCalor.name) } ,
                    onCloseSessionButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.Login.name, inclusive = false) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ProyectoSisvitaScreen.MapaCalor.name){
                PageMapaCalorScreen(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding),
                    onAcceptClicked = { navController.popBackStack(ProyectoSisvitaScreen.MenuPsicologo.name, inclusive = false)}
                )
            }

            composable(route = ProyectoSisvitaScreen.MenuTest.name){
                PageTestMenuSelection(
                    onCancelButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.MenuPaciente.name, inclusive = false) },
                    onTest1Clicked = { navController.navigate(ProyectoSisvitaScreen.Test1.name) },
                    onTest2Clicked = { navController.navigate(ProyectoSisvitaScreen.Test2.name)},
                    onTest3Clicked = { navController.navigate(ProyectoSisvitaScreen.Test3.name) },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )

            }

            composable(route = ProyectoSisvitaScreen.Test1.name) {
                PageTest1Screen(
                    onCancelButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.MenuTest.name, inclusive = false) },
                    onTestSubmitted = { navController.navigate(ProyectoSisvitaScreen.MenuTest.name) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            composable(route = ProyectoSisvitaScreen.Test2.name) {
                PageTest2Screen(
                    onCancelButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.MenuTest.name, inclusive = false) },
                    onTestSubmitted = { navController.navigate(ProyectoSisvitaScreen.MenuTest.name) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )

            }

            composable(route = ProyectoSisvitaScreen.Test3.name) {
                PageTest3Screen(
                    onCancelButtonClicked = { navController.popBackStack(ProyectoSisvitaScreen.MenuTest.name, inclusive = false) },
                    onTestSubmitted = { navController.navigate(ProyectoSisvitaScreen.MenuTest.name) },
                    loginViewModel = loginViewModel,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }
        }
    }
}
