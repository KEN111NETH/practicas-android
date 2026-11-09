
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

data class Tarea(
    val id: Int,
    val nombre: String,
    val prioridad: String,
    val completada: Boolean = false
)

@Composable
fun TareasScreen() {
    val tareas = remember { mutableStateListOf<Tarea>() }

    var nombre by remember { mutableStateOf("") }
    var prioridad by remember { mutableStateOf("Media") }
    var filtro by remember { mutableStateOf("Todas") }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var siguienteId by remember { mutableIntStateOf(0) }

    val tareasFiltradas = when (filtro) {
        "Pendientes" -> tareas.filter { !it.completada }
        "Hechas" -> tareas.filter { it.completada }
        else -> tareas.toList()
    }

    val pendientes = tareas.count { !it.completada }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text("Gestor de tareas", fontSize = 28.sp)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de la tarea") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Text("Prioridad")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Alta", "Media", "Baja").forEach {
                FilterChip(
                    selected = prioridad == it,
                    onClick = { prioridad = it },
                    label = { Text(it) }
                )
            }
        }

        Button(
            onClick = {
                if (nombre.isNotBlank()) {
                    tareas.add(
                        Tarea(
                            id = siguienteId++,
                            nombre = nombre.trim(),
                            prioridad = prioridad
                        )
                    )
                    nombre = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank()
        ) {
            Text("Agregar tarea")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Filtros")

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Todas", "Pendientes", "Hechas").forEach {
                FilterChip(
                    selected = filtro == it,
                    onClick = { filtro = it },
                    label = { Text(it) }
                )
            }
        }

        Text(
            "$pendientes pendientes de ${tareas.size}",
            fontSize = 18.sp
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(
                tareasFiltradas,
                key = { _, tarea -> tarea.id }
            ) { _, tarea ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = tarea.completada,
                        onCheckedChange = { marcada ->
                            val posicion = tareas.indexOfFirst {
                                it.id == tarea.id
                            }

                            if (posicion != -1) {
                                tareas[posicion] = tarea.copy(
                                    completada = marcada
                                )
                            }
                        }
                    )

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = tarea.nombre,
                            color = if (tarea.completada)
                                MaterialTheme.colorScheme.outline
                            else
                                MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            "Prioridad: ${tarea.prioridad}",
                            fontSize = 12.sp
                        )

                        if (tarea.completada) {
                            Text("Completada", fontSize = 12.sp)
                        }
                    }

                    TextButton(
                        onClick = { tareas.remove(tarea) }
                    ) {
                        Text("Eliminar")
                    }
                }

                HorizontalDivider()
            }
        }

        OutlinedButton(
            onClick = { mostrarDialogo = true },
            enabled = tareas.any { it.completada },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Borrar tareas completadas")
        }
    }

    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Quieres borrar todas las tareas completadas?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        tareas.removeAll { it.completada }
                        mostrarDialogo = false
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { mostrarDialogo = false }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PracticasandroidTheme {
                TareasScreen()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TareasPreview() {
    PracticasandroidTheme {
        TareasScreen()
    }
}
