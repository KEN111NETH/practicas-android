
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

data class Producto(
    val nombre: String,
    val precio: Double
)

@Composable
fun CarritoScreen() {
    val productos = listOf(
        Producto("Tortillas 1 kg", 24.0),
        Producto("Leche 1 L", 27.50),
        Producto("Huevo 12 piezas", 52.0),
        Producto("Frijol 1 kg", 38.0)
    )

    val cantidades = remember { mutableStateListOf(0, 0, 0, 0) }
    var confirmado by remember { mutableStateOf(false) }

    val subtotal = productos.indices.sumOf {
        productos[it].precio * cantidades[it]
    }

    val descuento = if (subtotal >= 200) subtotal * 0.10 else 0.0
    val totalConDescuento = subtotal - descuento
    val envio = if (subtotal == 0.0) 0.0
    else if (totalConDescuento >= 150) 0.0 else 25.0
    val total = totalConDescuento + envio

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text("Carrito Don Pepe", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        productos.forEachIndexed { index, producto ->
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
                    Column {
                        Text(producto.nombre)
                        Text("$${producto.precio}")
                    }

                    Row {
                        Button(
                            onClick = {
                                if (cantidades[index] > 0) {
                                    cantidades[index]--
                                }
                            },
                            enabled = cantidades[index] > 0
                        ) {
                            Text("-")
                        }

                        Text(
                            text = "${cantidades[index]}",
                            modifier = Modifier.padding(12.dp)
                        )

                        Button(
                            onClick = {
                                if (cantidades[index] < 10) {
                                    cantidades[index]++
                                }
                            },
                            enabled = cantidades[index] < 10
                        ) {
                            Text("+")
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Resumen de compra", fontSize = 22.sp)
        Text("Subtotal: $${"%.2f".format(subtotal)}")
        Text("Descuento: $${"%.2f".format(descuento)}")
        Text("Envío: $${"%.2f".format(envio)}")

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = "Total: $${"%.2f".format(total)}",
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { confirmado = true },
            enabled = subtotal > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Confirmar compra")
        }

        OutlinedButton(
            onClick = {
                cantidades.indices.forEach {
                    cantidades[it] = 0
                }
            },
            enabled = subtotal > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vaciar carrito")
        }

        if (confirmado) {
            AlertDialog(
                onDismissRequest = { confirmado = false },
                title = { Text("¡Compra confirmada!") },
                text = {
                    Column {
                        productos.forEachIndexed { index, producto ->
                            if (cantidades[index] > 0) {
                                Text("${producto.nombre}: ${cantidades[index]}")
                            }
                        }
                        Text("Total pagado: $${"%.2f".format(total)}")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            cantidades.indices.forEach {
                                cantidades[it] = 0
                            }
                            confirmado = false
                        }
                    ) {
                        Text("Aceptar")
                    }
                }
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                CarritoScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarritoPreview() {
    PracticasandroidTheme {
        CarritoScreen()
    }
}
