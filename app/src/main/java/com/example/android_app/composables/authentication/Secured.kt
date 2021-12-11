package com.example.android_app.composables.authentication

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun Secured(isAuthenticated: () -> Boolean, content: @Composable () -> Unit) {
    if(isAuthenticated()){
        content()
    }else{
        Text("Authentication Required")
    }
}