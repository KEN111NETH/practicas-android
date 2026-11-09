
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
fun CalculadoraScreen() {
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("Resultado") }

    fun calcular(operacion: String) {
        val n1 = numero1.toDoubleOrNull()
        val n2 = numero2.toDoubleOrNull()

        if (n1 == null || n2 == null) {
            resultado = "Ingresa dos números válidos"
            return
        }

        val total = when (operacion) {
            "+" -> n1 + n2
            "-" -> n1 - n2
            "*" -> n1 * n2
            "/" -> {
                if (n2 == 0.0) {
                    resultado = "No se puede dividir entre cero"
                    return
                }
                n1 / n2
            }
            else -> 0.0
        }

        resultado = "Resultado: $total"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Mi calculadora", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = numero1,
            onValueChange = { numero1 = it },
            label = { Text("Primer número") }
        )

        OutlinedTextField(
            value = numero2,
            onValueChange = { numero2 = it },
            label = { Text("Segundo número") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { calcular("+") }) {
                Text("+")
            }
            Button(onClick = { calcular("-") }) {
                Text("-")
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { calcular("*") }) {
                Text("×")
            }
            Button(onClick = { calcular("/") }) {
                Text("÷")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(resultado, fontSize = 22.sp)

        TextButton(
            onClick = {
                numero1 = ""
                numero2 = ""
                resultado = "Resultado"
            }
        ) {
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
                CalculadoraScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    PracticasandroidTheme {
        CalculadoraScreen()
    }
}
