
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
import kotlin.math.ceil

@Composable
fun CuentaScreen() {
    var montoTexto by remember { mutableStateOf("500") }
    var propina by remember { mutableFloatStateOf(15f) }
    var otraPropina by remember { mutableStateOf(false) }
    var personas by remember { mutableIntStateOf(2) }
    var redondear by remember { mutableStateOf(false) }

    val monto = montoTexto.toDoubleOrNull() ?: 0.0
    val propinaTotal = monto * (propina / 100)
    val total = monto + propinaTotal
    val porPersona = total / personas
    val cantidadFinal = if (redondear) ceil(porPersona) else porPersona
    val sobrante = (cantidadFinal * personas) - total

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Cuenta entre amigos", fontSize = 28.sp)

        Text("Monto de la cuenta")

        OutlinedTextField(
            value = montoTexto,
            onValueChange = {
                montoTexto = it.filter { c -> c.isDigit() || c == '.' }
            },
            label = { Text("Cantidad en pesos") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (montoTexto.isNotEmpty() && montoTexto.toDoubleOrNull() == null) {
            Text("Escribe una cantidad válida", color = MaterialTheme.colorScheme.error)
        }

        Text("Propina: ${propina.toInt()}%")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf(10f, 15f, 20f).forEach {
                FilterChip(
                    selected = !otraPropina && propina == it,
                    onClick = {
                        propina = it
                        otraPropina = false
                    },
                    label = { Text("${it.toInt()}%") }
                )
            }

            FilterChip(
                selected = otraPropina,
                onClick = { otraPropina = true },
                label = { Text("Otro") }
            )
        }

        if (otraPropina) {
            Slider(
                value = propina,
                onValueChange = { propina = it },
                valueRange = 0f..30f
            )
            Text("${propina.toInt()}% de propina")
        }

        HorizontalDivider()

        Text("Número de personas: $personas")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { if (personas > 1) personas-- },
                enabled = personas > 1
            ) {
                Text("-")
            }

            Text(
                "$personas",
                modifier = Modifier.padding(horizontal = 24.dp),
                fontSize = 20.sp
            )

            Button(
                onClick = { if (personas < 20) personas++ },
                enabled = personas < 20
            ) {
                Text("+")
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = redondear,
                onCheckedChange = { redondear = it }
            )
            Text("Redondear hacia arriba")
        }

        HorizontalDivider()

        Text("Resumen", fontSize = 22.sp)

        Text("Cuenta: $${"%.2f".format(monto)}")
        Text("Propina: $${"%.2f".format(propinaTotal)}")

        Text(
            "Total: $${"%.2f".format(total)}",
            fontSize = 20.sp
        )

        Text(
            "Cada persona paga: $${"%.2f".format(cantidadFinal)}",
            fontSize = 24.sp,
            color = MaterialTheme.colorScheme.primary
        )

        if (redondear) {
            Text("Sobrante por redondeo: $${"%.2f".format(sobrante)}")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                CuentaScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CuentaPreview() {
    PracticasandroidTheme {
        CuentaScreen()
    }
}
