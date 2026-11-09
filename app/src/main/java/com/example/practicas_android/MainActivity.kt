
package com.example.practicas_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

@Composable
fun InicialesScreen() {
    var nombre by remember { mutableStateOf("") }
    var iniciales by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Generador de iniciales", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                iniciales = ""
            },
            label = { Text("Escribe tu nombre completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                iniciales = nombre.trim()
                    .split(Regex("\\s+"))
                    .filter { it.isNotEmpty() }
                    .map { it.first().uppercaseChar() }
                    .joinToString(".")
            },
            enabled = nombre.isNotBlank()
        ) {
            Text("Generar iniciales")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = iniciales,
            fontSize = 36.sp
        )

        TextButton(onClick = {
            nombre = ""
            iniciales = ""
        }) {
            Text("Limpiar")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                InicialesScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InicialesPreview() {
    PracticasandroidTheme {
        InicialesScreen()
    }
}
