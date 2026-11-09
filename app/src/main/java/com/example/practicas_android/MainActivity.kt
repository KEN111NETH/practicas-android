
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
import kotlin.random.Random

@Composable
fun SumaScreen() {
    var numero1 by remember { mutableIntStateOf(Random.nextInt(1, 20)) }
    var numero2 by remember { mutableIntStateOf(Random.nextInt(1, 20)) }
    var respuesta by remember { mutableStateOf("") }
    var puntos by remember { mutableIntStateOf(0) }
    var mensaje by remember { mutableStateOf("") }
    var respondida by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Juego de sumas", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(20.dp))

        Text("Puntos: $puntos", fontSize = 22.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text("$numero1 + $numero2 = ?", fontSize = 36.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = respuesta,
            onValueChange = {
                if (it.all { caracter -> caracter.isDigit() }) {
                    respuesta = it
                }
            },
            label = { Text("Tu respuesta") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val valor = respuesta.toIntOrNull()

                if (valor == null) {
                    mensaje = "Escribe una respuesta"
                } else if (valor == numero1 + numero2) {
                    puntos++
                    mensaje = "¡Correcto!"
                    respondida = true
                } else {
                    mensaje = "Incorrecto. Era ${numero1 + numero2}"
                    respondida = true
                }
            },
            enabled = !respondida
        ) {
            Text("Comprobar")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(mensaje, fontSize = 20.sp)

        if (respondida) {
            Button(onClick = {
                numero1 = Random.nextInt(1, 20)
                numero2 = Random.nextInt(1, 20)
                respuesta = ""
                mensaje = ""
                respondida = false
            }) {
                Text("Siguiente suma")
            }
        }

        TextButton(onClick = {
            puntos = 0
            numero1 = Random.nextInt(1, 20)
            numero2 = Random.nextInt(1, 20)
            respuesta = ""
            mensaje = ""
            respondida = false
        }) {
            Text("Reiniciar juego")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                SumaScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SumaPreview() {
    PracticasandroidTheme {
        SumaScreen()
    }
}
