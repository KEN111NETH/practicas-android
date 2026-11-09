
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

data class Pregunta(
    val texto: String,
    val opciones: List<String>,
    val correcta: Int
)

val preguntas = listOf(
    Pregunta(
        "¿Qué palabra se utiliza para declarar una variable constante en Kotlin?",
        listOf("var", "val", "int", "constante"),
        1
    ),
    Pregunta(
        "¿Qué tipo de dato representa un número entero?",
        listOf("String", "Boolean", "Int", "Double"),
        2
    ),
    Pregunta(
        "¿Qué función inicia una aplicación Android?",
        listOf("onCreate", "mainScreen", "startApp", "init"),
        0
    ),
    Pregunta(
        "¿Qué lenguaje se utiliza principalmente con Jetpack Compose?",
        listOf("Java", "Python", "C++", "Kotlin"),
        3
    ),
    Pregunta(
        "¿Qué función permite mostrar texto en Compose?",
        listOf("Print", "Text", "Show", "Display"),
        1
    )
)

@Composable
fun QuizScreen() {
    var nombre by remember { mutableStateOf("") }
    var pantalla by remember { mutableStateOf(0) }
    var numero by remember { mutableIntStateOf(0) }
    var seleccion by remember { mutableIntStateOf(-1) }
    var respondida by remember { mutableStateOf(false) }
    var puntos by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (pantalla) {
            0 -> {
                Text("Quiz de programación", fontSize = 28.sp)
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Tu nombre") }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { pantalla = 1 },
                    enabled = nombre.trim().length >= 2
                ) {
                    Text("Comenzar")
                }
            }

            1 -> {
                val pregunta = preguntas[numero]

                Text("Jugador: $nombre")
                Text("Pregunta ${numero + 1} de ${preguntas.size}")

                LinearProgressIndicator(
                    progress = { (numero + 1).toFloat() / preguntas.size },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(pregunta.texto, fontSize = 20.sp)

                pregunta.opciones.forEachIndexed { index, opcion ->
                    val color = when {
                        respondida && index == pregunta.correcta ->
                            MaterialTheme.colorScheme.primary
                        respondida && index == seleccion ->
                            MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = seleccion == index,
                            onClick = { seleccion = index },
                            enabled = !respondida
                        )
                        Text(opcion, color = color)
                    }
                }

                if (respondida) {
                    Text(
                        if (seleccion == pregunta.correcta)
                            "¡Respuesta correcta!"
                        else
                            "Respuesta incorrecta. Era: ${pregunta.opciones[pregunta.correcta]}"
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (!respondida) {
                            if (seleccion == pregunta.correcta) puntos++
                            respondida = true
                        } else {
                            if (numero < preguntas.lastIndex) {
                                numero++
                                seleccion = -1
                                respondida = false
                            } else {
                                pantalla = 2
                            }
                        }
                    },
                    enabled = seleccion != -1,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        when {
                            !respondida -> "Confirmar respuesta"
                            numero == preguntas.lastIndex -> "Ver resultados"
                            else -> "Siguiente pregunta"
                        }
                    )
                }
            }

            2 -> {
                Text("Resultados", fontSize = 28.sp)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Jugador: $nombre")
                Text("Obtuviste $puntos de 5 puntos", fontSize = 20.sp)

                Text(
                    when (puntos) {
                        in 0..2 -> "Sigue practicando"
                        in 3..4 -> "¡Buen trabajo!"
                        else -> "¡Perfecto!"
                    },
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        numero = 0
                        seleccion = -1
                        respondida = false
                        puntos = 0
                        pantalla = 1
                    }
                ) {
                    Text("Volver a intentar")
                }

                TextButton(
                    onClick = {
                        nombre = ""
                        numero = 0
                        seleccion = -1
                        respondida = false
                        puntos = 0
                        pantalla = 0
                    }
                ) {
                    Text("Cambiar jugador")
                }
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
                QuizScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizPreview() {
    PracticasandroidTheme {
        QuizScreen()
    }
}
