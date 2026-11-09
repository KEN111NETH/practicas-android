
package com.example.practicas_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

enum class Interlineado(val valor: Float) {
    Compacto(1f),
    Normal(1.3f),
    Amplio(1.6f)
}

@Composable
fun MainActivityScreen() {
    var tamano by remember { mutableFloatStateOf(18f) }
    var interlineado by remember { mutableStateOf(Interlineado.Normal) }
    var modoOscuro by remember { mutableStateOf(false) }
    var usaSerif by remember { mutableStateOf(false) }

    val fondo = if (modoOscuro) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background
    val texto = if (modoOscuro) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onBackground

    MaterialTheme(
        colorScheme = if (modoOscuro) darkColorScheme() else lightColorScheme()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text("Ajustes de lectura", fontSize = 26.sp)

            Spacer(modifier = Modifier.height(20.dp))

            Text("Tamaño de letra: ${tamano.toInt()} sp")

            Slider(
                value = tamano,
                onValueChange = { tamano = it },
                valueRange = 14f..32f
            )

            Text("Interlineado")

            Row {
                Interlineado.entries.forEach { opcion ->
                    Column(modifier = Modifier.weight(1f)) {
                        RadioButton(
                            selected = interlineado == opcion,
                            onClick = { interlineado = opcion }
                        )
                        Text(opcion.name)
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo oscuro")
                Switch(
                    checked = modoOscuro,
                    onCheckedChange = { modoOscuro = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Tipografía Serif")
                FilterChip(
                    selected = usaSerif,
                    onClick = { usaSerif = !usaSerif },
                    label = { Text(if (usaSerif) "Serif" else "Sans") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text("Vista previa", fontSize = 20.sp)

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                color = fondo,
                tonalElevation = 2.dp
            ) {
                Text(
                    text = "La lectura es una herramienta que nos permite aprender cosas nuevas, desarrollar nuestra imaginación y conocer diferentes ideas. Ajusta las opciones para encontrar la forma más cómoda de leer.",
                    modifier = Modifier.padding(16.dp),
                    fontSize = tamano.sp,
                    lineHeight = (tamano * interlineado.valor).sp,
                    fontFamily = if (usaSerif) FontFamily.Serif else FontFamily.SansSerif,
                    color = texto
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    tamano = 18f
                    interlineado = Interlineado.Normal
                    modoOscuro = false
                    usaSerif = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restablecer ajustes")
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
                MainActivityScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LecturaPreview() {
    PracticasandroidTheme {
        MainActivityScreen()
    }
}
