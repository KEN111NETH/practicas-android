
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
fun RegistroScreen() {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var registrado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!registrado) {
            Text("Crear cuenta", fontSize = 28.sp)

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = confirmar,
                onValueChange = { confirmar = it },
                label = { Text("Confirmar contraseña") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (mensaje.isNotEmpty()) {
                Text(
                    mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Button(
                onClick = {
                    mensaje = when {
                        nombre.isBlank() -> "Escribe tu nombre"
                        !correo.contains("@") || !correo.contains(".") ->
                            "Ingresa un correo válido"
                        password.length < 6 ->
                            "La contraseña necesita 6 caracteres"
                        password != confirmar ->
                            "Las contraseñas no coinciden"
                        else -> {
                            registrado = true
                            ""
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrarme")
            }
        } else {
            Text("¡Registro exitoso!", fontSize = 26.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Bienvenido, $nombre")
            Text("Correo: $correo")

            Button(onClick = {
                nombre = ""
                correo = ""
                password = ""
                confirmar = ""
                mensaje = ""
                registrado = false
            }) {
                Text("Nuevo registro")
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                RegistroScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    PracticasandroidTheme {
        RegistroScreen()
    }
}
