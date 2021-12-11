package com.example.android_app.dummy

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun DummyScreen(modifier: Modifier = Modifier, message: String = "This is a dummy screen", onAction: () -> Unit){
    Column(modifier.padding(16.dp)) {
        Text(message)
        Spacer(modifier = Modifier.size(8.dp))
        Button(onClick = onAction) {
            Text("Click me!")
        }
    }
}

