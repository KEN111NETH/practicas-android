
package com.example.practicas_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

@Composable
fun ContadorScreen() {
    var contador by remember { mutableIntStateOf(0) }
    val historial = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mi contador", fontSize = 28.sp)

        Text(
            contador.toString(),
            fontSize = 60.sp
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(onClick = {
                contador--
                historial.add("Restaste 1: $contador")
            }) {
                Text("-")
            }

            Button(onClick = {
                contador++
                historial.add("Sumaste 1: $contador")
            }) {
                Text("+")
            }
        }

        Button(onClick = {
            contador = 0
            historial.add("Reiniciaste el contador")
        }) {
            Text("Reiniciar")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Historial", fontSize = 22.sp)

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(historial.reversed()) { movimiento ->
                Text(
                    movimiento,
                    modifier = Modifier.padding(6.dp)
                )
            }
        }

        TextButton(onClick = {
            historial.clear()
        }) {
            Text("Borrar historial")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                ContadorScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContadorPreview() {
    PracticasandroidTheme {
        ContadorScreen()
    }
}
