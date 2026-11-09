
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practicas_android.ui.theme.PracticasandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                AforoScreen()
            }
        }
    }
}

@Composable
fun AforoScreen() {
    var personasActuales by remember { mutableStateOf(0) }
    var visitasDelDia by remember { mutableStateOf(0) }

    val capacidadMaxima = 50
    val porcentaje = personasActuales.toFloat() / capacidadMaxima
    val colorEstado = when {
        porcentaje >= 1f -> Color.Red
        porcentaje >= 0.7f -> Color(0xFFFFC107)
        else -> Color(0xFF4CAF50)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Biblioteca Central",
            fontSize = 26.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Aforo actual",
            fontSize = 20.sp
        )

        Text(
            text = "$personasActuales / $capacidadMaxima",
            fontSize = 36.sp,
            color = colorEstado
        )

        LinearProgressIndicator(
            progress = { porcentaje },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = colorEstado
        )

        Text(
            text = "${(porcentaje * 100).toInt()}% de ocupación",
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = when {
                porcentaje >= 1f -> "Aforo completo"
                porcentaje >= 0.7f -> "Capacidad casi completa"
                else -> "Hay espacio disponible"
            }
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (personasActuales < capacidadMaxima) {
                        personasActuales++
                        visitasDelDia++
                    }
                },
                enabled = personasActuales < capacidadMaxima
            ) {
                Text("Entra")
            }

            Button(
                onClick = {
                    if (personasActuales > 0) {
                        personasActuales--
                    }
                },
                enabled = personasActuales > 0
            ) {
                Text("Sale")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Visitas del día: $visitasDelDia",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                personasActuales = 0
                visitasDelDia = 0
            }
        ) {
            Text("Cerrar día")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AforoPreview() {
    PracticasandroidTheme {
        AforoScreen()
    }
}
