
package com.example.practicas_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.practicas_android.ui.theme.PracticasandroidTheme

@Composable
fun ListaComprasScreen() {
    var producto by remember { mutableStateOf("") }
    val productos = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Lista de compras", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = producto,
            onValueChange = { producto = it },
            label = { Text("Nombre del producto") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                if (producto.isNotBlank()) {
                    productos.add(producto.trim())
                    producto = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Agregar producto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Productos: ${productos.size}", fontSize = 20.sp)

        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(productos) { index, nombre ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${index + 1}. $nombre")

                    TextButton(onClick = {
                        productos.removeAt(index)
                    }) {
                        Text("Eliminar")
                    }
                }

                HorizontalDivider()
            }
        }

        Button(
            onClick = { productos.clear() },
            enabled = productos.isNotEmpty(),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Vaciar lista")
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                ListaComprasScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListaComprasPreview() {
    PracticasandroidTheme {
        ListaComprasScreen()
    }
}
