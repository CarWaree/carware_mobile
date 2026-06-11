package com.example.carware

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composition
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.carware.nav.HomeScreen
import com.example.carware.nav.LoginScreen
import com.example.carware.nav.NewPasswordScreen
import com.example.carware.nav.Screens.HomeScreen
import com.example.carware.nav.Screens.LoginScreen
import com.example.carware.nav.ResetPasswordScreen
import com.example.carware.nav.Screens.NewPasswordScreen
import com.example.carware.nav.VerficationCodeScreen
import com.example.carware.nav.Screens.ResetPasswordScreen
import com.example.carware.nav.Screens.SignUpScreen
import com.example.carware.nav.Screens.VerficationCodeScreen
import com.example.carware.nav.SignUpScreen
import com.example.carware.ui.theme.CarWareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            CarWareTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController=navController,
                        startDestination = SignUpScreen,
                        modifier = Modifier.padding(innerPadding))
                        {
                            composable<SignUpScreen> {
                                SignUpScreen(navController)
                            }
                            composable<LoginScreen> {
                                LoginScreen(navController)
                            }
                            composable<HomeScreen> {
                                HomeScreen(navController)
                            }
                            composable<ResetPasswordScreen> {
                                ResetPasswordScreen(navController)
                            }
                            composable<VerficationCodeScreen> {
                                VerficationCodeScreen(navController)
                            }
                            composable<NewPasswordScreen> {
                                NewPasswordScreen(navController)
                            }



                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello World",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CarWareTheme {
        Greeting("Android")
    }
}