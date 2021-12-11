package com.example.android_app

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.android_app.composables.authentication.LoginScreen
import com.example.android_app.dummy.DummyScreen
import com.example.android_app.navigation.AppNavigator
import com.example.android_app.state.rememberAuthenticationState
import com.example.android_app.ui.theme.Android_appTheme

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Android_appTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MyApp()
                }
            }
        }
    }
}


@ExperimentalComposeUiApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp() {
    Scaffold(topBar = { TopAppBar(title = { Text("Books App") }) }) { innerPadding ->
        AppNavigator(modifier = Modifier.padding(innerPadding))
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Android_appTheme {
        Greeting("Android")
    }
}