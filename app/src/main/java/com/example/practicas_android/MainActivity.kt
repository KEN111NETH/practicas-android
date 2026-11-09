
package com.example.practicas_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                InscripcionScreen()
            }
        }
    }
}

@Composable
fun InscripcionScreen() {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var taller by remember { mutableStateOf("Programación") }
    var privacidad by remember { mutableStateOf(false) }
    var enviado by remember { mutableStateOf(false) }
    var mostrarErrores by remember { mutableStateOf(false) }

    val nombreValido = nombre.trim().length >= 3
    val correoValido = correo.contains("@") &&
            correo.substringAfter("@").contains(".")
    val edadValida = (edad.toIntOrNull() ?: 0) in 12..99

    if (enviado) {
        ConfirmacionScreen(
            nombre = nombre,
            correo = correo,
            edad = edad,
            taller = taller,
            onNuevo = {
                nombre = ""
                correo = ""
                edad = ""
                taller = "Programación"
                privacidad = false
                enviado = false
                mostrarErrores = false
            }
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Casa del Lago",
                fontSize = 28.sp
            )

            Text(
                text = "Inscripción a talleres",
                fontSize = 20.sp
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (mostrarErrores && !nombreValido) {
                Text("Escribe al menos 3 caracteres", color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (mostrarErrores && !correoValido) {
                Text("Correo no válido", color = MaterialTheme.colorScheme.error)
            }

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it.filter { c -> c.isDigit() } },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            if (mostrarErrores && !edadValida) {
                Text("La edad debe estar entre 12 y 99 años", color = MaterialTheme.colorScheme.error)
            }

            Text("Selecciona un taller")

            var expandido by remember { mutableStateOf(false) }

            Box {
                OutlinedButton(
                    onClick = { expandido = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(taller)
                }

                DropdownMenu(
                    expanded = expandido,
                    onDismissRequest = { expandido = false }
                ) {
                    listOf("Programación", "Pintura", "Fotografía", "Música").forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                taller = it
                                expandido = false
                            }
                        )
                    }
                }
            }

            Row {
                Checkbox(
                    checked = privacidad,
                    onCheckedChange = { privacidad = it }
                )
                Text(
                    text = "Acepto el aviso de privacidad",
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            if (mostrarErrores && !privacidad) {
                Text("Debes aceptar el aviso de privacidad", color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    mostrarErrores = true
                    if (nombreValido && correoValido && edadValida && privacidad) {
                        enviado = true
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Inscribirme")
            }
        }
    }
}

@Composable
fun ConfirmacionScreen(
    nombre: String,
    correo: String,
    edad: String,
    taller: String,
    onNuevo: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "¡Inscripción exitosa!",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Nombre: $nombre")
        Text("Correo: $correo")
        Text("Edad: $edad años")
        Text("Taller: $taller")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onNuevo,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Nueva inscripción")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InscripcionPreview() {
    PracticasandroidTheme {
        InscripcionScreen()
    }
}
