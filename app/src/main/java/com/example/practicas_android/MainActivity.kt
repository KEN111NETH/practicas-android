
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

@Composable
fun RatingBar(
    titulo: String,
    calificacion: Int,
    onCalificar: (Int) -> Unit
) {
    Column {
        Text(titulo)
        Row {
            for (i in 1..5) {
                TextButton(onClick = { onCalificar(i) }) {
                    Text(
                        if (i <= calificacion) "★" else "☆",
                        fontSize = 28.sp
                    )
                }
            }
        }
        Text(
            when (calificacion) {
                1 -> "Malo"
                2 -> "Regular"
                3 -> "Bueno"
                4 -> "Muy bueno"
                5 -> "Excelente"
                else -> "Sin calificar"
            }
        )
    }
}

@Composable
fun ResenaScreen() {
    var comida by remember { mutableIntStateOf(0) }
    var servicio by remember { mutableIntStateOf(0) }
    var ambiente by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }
    var publicada by remember { mutableStateOf(false) }

    val etiquetas = listOf(
        "Familiar", "Romántico", "Económico",
        "Vegetariano", "Rápido"
    )

    val seleccionadas = remember { mutableStateListOf<String>() }

    val promedio = (comida + servicio + ambiente) / 3f
    val puedePublicar = comida > 0 &&
            servicio > 0 &&
            ambiente > 0 &&
            comentario.length >= 20

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reseñas de restaurantes", fontSize = 25.sp)

        Spacer(modifier = Modifier.height(20.dp))

        if (!publicada) {
            RatingBar("Comida", comida) { comida = it }
            RatingBar("Servicio", servicio) { servicio = it }
            RatingBar("Ambiente", ambiente) { ambiente = it }

            Spacer(modifier = Modifier.height(16.dp))

            Text("¿Qué te pareció el lugar?")

            etiquetas.chunked(2).forEach { fila ->
                Row {
                    fila.forEach { etiqueta ->
                        FilterChip(
                            selected = etiqueta in seleccionadas,
                            onClick = {
                                if (etiqueta in seleccionadas) {
                                    seleccionadas.remove(etiqueta)
                                } else {
                                    seleccionadas.add(etiqueta)
                                }
                            },
                            label = { Text(etiqueta) },
                            modifier = Modifier.padding(end = 5.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = comentario,
                onValueChange = {
                    if (it.length <= 200) comentario = it
                },
                label = { Text("Escribe tu comentario") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Text(
                "${comentario.length}/200",
                color = if (comentario.length >= 180)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface
            )

            if (comentario.isNotEmpty() && comentario.length < 20) {
                Text(
                    "El comentario debe tener mínimo 20 caracteres",
                    color = MaterialTheme.colorScheme.error
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { publicada = true },
                enabled = puedePublicar,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Publicar reseña")
            }
        } else {
            Text("¡Reseña publicada!", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Text("Comida: $comida/5")
            Text("Servicio: $servicio/5")
            Text("Ambiente: $ambiente/5")
            Text("Promedio: ${"%.1f".format(promedio)} / 5")

            Spacer(modifier = Modifier.height(12.dp))

            Text("Comentario:")
            Text(comentario)

            Spacer(modifier = Modifier.height(12.dp))

            Text("Etiquetas: ${seleccionadas.joinToString(", ").ifEmpty { "Ninguna" }}")

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    comida = 0
                    servicio = 0
                    ambiente = 0
                    comentario = ""
                    seleccionadas.clear()
                    publicada = false
                }
            ) {
                Text("Nueva reseña")
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
                ResenaScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResenaPreview() {
    PracticasandroidTheme {
        ResenaScreen()
    }
}
