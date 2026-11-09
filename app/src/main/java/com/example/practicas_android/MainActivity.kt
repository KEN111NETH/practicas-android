
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

@Composable
fun LoginScreen() {
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var recordar by remember { mutableStateOf(false) }
    var mostrarPassword by remember { mutableStateOf(false) }
    var intentos by remember { mutableIntStateOf(0) }
    var sesionIniciada by remember { mutableStateOf(false) }
    var mensaje by remember { mutableStateOf("") }

    if (sesionIniciada) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("¡Bienvenido a Clínica Vida!", fontSize = 26.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text("Sesión iniciada con: $correo")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    correo = ""
                    password = ""
                    recordar = false
                    intentos = 0
                    mensaje = ""
                    sesionIniciada = false
                }
            ) {
                Text("Cerrar sesión")
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Clínica Vida", fontSize = 30.sp)

            Text("Inicio de sesión", fontSize = 20.sp)

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = if (mostrarPassword)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                trailingIcon = {
                    TextButton(
                        onClick = { mostrarPassword = !mostrarPassword }
                    ) {
                        Text(if (mostrarPassword) "Ocultar" else "Mostrar")
                    }
                }
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = recordar,
                    onCheckedChange = { recordar = it }
                )
                Text("Recordarme")
            }

            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    if (correo == "paciente@vida.mx" &&
                        password == "Vida2025"
                    ) {
                        sesionIniciada = true
                        mensaje = ""
                    } else {
                        intentos++
                        mensaje = if (intentos >= 3) {
                            "Cuenta bloqueada. Demasiados intentos."
                        } else {
                            "Datos incorrectos. Intento $intentos de 3."
                        }
                    }
                },
                enabled = intentos < 3,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
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
                LoginScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    PracticasandroidTheme {
        LoginScreen()
    }
}
