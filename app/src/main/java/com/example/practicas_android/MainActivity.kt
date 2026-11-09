
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

data class Gasto(
    val concepto: String,
    val cantidad: Double,
    val categoria: String
)

@Composable
fun PresupuestoScreen() {
    var ingreso by remember { mutableStateOf("4500") }
    var concepto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("Comida") }
    var mostrarCategorias by remember { mutableStateOf(false) }

    val gastos = remember { mutableStateListOf<Gasto>() }
    val categorias = listOf("Comida", "Transporte", "Renta", "Ocio", "Otros")

    val totalIngreso = ingreso.toDoubleOrNull() ?: 0.0
    val totalGastos = gastos.sumOf { it.cantidad }
    val saldo = totalIngreso - totalGastos
    val porcentaje = if (totalIngreso > 0) {
        (totalGastos / totalIngreso).toFloat().coerceIn(0f, 1f)
    } else {
        0f
    }

    val categoriaMayor = gastos
        .groupBy { it.categoria }
        .mapValues { grupo -> grupo.value.sumOf { it.cantidad } }
        .maxByOrNull { it.value }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Presupuesto mensual", fontSize = 26.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = ingreso,
            onValueChange = { ingreso = it },
            label = { Text("Ingreso mensual") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Resumen del mes", fontSize = 20.sp)
                Text("Ingreso: $${"%.2f".format(totalIngreso)}")
                Text("Gastado: $${"%.2f".format(totalGastos)}")
                Text("Saldo: $${"%.2f".format(saldo)}")

                Spacer(modifier = Modifier.height(12.dp))

                LinearProgressIndicator(
                    progress = { porcentaje },
                    modifier = Modifier.fillMaxWidth()
                )

                Text("${(porcentaje * 100).toInt()}% del ingreso utilizado")

                if (totalGastos > totalIngreso && totalIngreso > 0) {
                    Text(
                        "¡Presupuesto excedido por $${"%.2f".format(totalGastos - totalIngreso)}!",
                        color = MaterialTheme.colorScheme.error
                    )
                } else if (porcentaje >= 0.8f && totalIngreso > 0) {
                    Text(
                        "¡Cuidado! Ya utilizaste el 80% de tu presupuesto.",
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

                if (categoriaMayor != null) {
                    Text("Mayor gasto: ${categoriaMayor.key}")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Agregar gasto", fontSize = 20.sp)

        OutlinedTextField(
            value = concepto,
            onValueChange = { concepto = it },
            label = { Text("Concepto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            modifier = Modifier.fillMaxWidth()
        )

        Box {
            TextButton(onClick = { mostrarCategorias = true }) {
                Text("Categoría: $categoria")
            }

            DropdownMenu(
                expanded = mostrarCategorias,
                onDismissRequest = { mostrarCategorias = false }
            ) {
                categorias.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            categoria = it
                            mostrarCategorias = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val valor = cantidad.toDoubleOrNull()
                if (concepto.isNotBlank() && valor != null && valor > 0) {
                    gastos.add(Gasto(concepto, valor, categoria))
                    concepto = ""
                    cantidad = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar gasto")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Mis gastos", fontSize = 20.sp)

        if (gastos.isEmpty()) {
            Text("Todavía no hay gastos registrados.")
        }

        gastos.forEachIndexed { index, gasto ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(gasto.concepto)
                        Text(gasto.categoria)
                    }

                    Column {
                        Text("$${"%.2f".format(gasto.cantidad)}")
                        TextButton(onClick = { gastos.removeAt(index) }) {
                            Text("Eliminar")
                        }
                    }
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
                PresupuestoScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PresupuestoPreview() {
    PracticasandroidTheme {
        PresupuestoScreen()
    }
}
