package com.example.kierunkistudiowcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels


data class DaneKierunku(
    val id: Int,
    var nazwa: String,
    var skrot: String
)


class MainActivity : ComponentActivity() {
    private val viewModel: KierunkiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                KierunkiScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun KierunkiScreen(viewModel: KierunkiViewModel) {
    var nazwa by remember { mutableStateOf("") }
    var skrot by remember { mutableStateOf("") }

    var filtr by remember { mutableStateOf("") }

    val wszystkieKierunki by remember { derivedStateOf {
        if(filtr.isBlank()) viewModel.listaKierunkow
        else viewModel.wyszukajKierunki(filtr)
    } }

    Column(modifier = Modifier.padding(8.dp)) {
        Text("Dodaj kierunek", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = nazwa,
            onValueChange = { nazwa = it },
            label = { Text("Nazwa") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp)
        )

        OutlinedTextField(
            value = skrot,
            onValueChange = { skrot = it },
            label = { Text("Skrót") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp)
        )

        Button(
            onClick = {
                if (nazwa.isNotBlank() && skrot.isNotBlank()) {
                    viewModel.dodajKierunek(nazwa, skrot)
                    nazwa = ""
                    skrot = ""
                }
            },
            modifier = Modifier.padding(vertical = 1.dp).fillMaxWidth()
        ) {
            Text("Dodaj")
        }

        OutlinedTextField(
            value = filtr,
            onValueChange =  {filtr = it},
            label = {Text("Wyszukaj kierunek")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        )

        Button(
          onClick = {viewModel.sortujKierunki()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Sortuj")
        }

        Text("Lista kierunków", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(wszystkieKierunki, key = { it.id }) { kierunek ->
                KierunekCard(kierunek, viewModel)
            }
        }
    }
}

@Composable
fun KierunekCard(kierunek: DaneKierunku, viewModel: KierunkiViewModel) {
    var isEditing by remember { mutableStateOf(false) }
    var edytowanaNazwa by remember { mutableStateOf(kierunek.nazwa) }
    var edytowanySkrot by remember { mutableStateOf(kierunek.skrot) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text("ID: ${kierunek.id}", style = MaterialTheme.typography.labelSmall)

            if (isEditing) {
                OutlinedTextField(
                    value = edytowanaNazwa,
                    onValueChange = { edytowanaNazwa = it },
                    label = { Text("Nazwa") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                OutlinedTextField(
                    value = edytowanySkrot,
                    onValueChange = { edytowanySkrot = it },
                    label = { Text("Skrót") },
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        viewModel.aktualizujKierunek(kierunek.id, edytowanaNazwa, edytowanySkrot)
                        isEditing = false
                    }) {
                        Text("Zapisz")
                    }
                    TextButton(onClick = {
                        isEditing = false
                    }) {
                        Text("Anuluj")
                    }
                }
            } else {
                Text("Nazwa: ${kierunek.nazwa}")
                Text("Skrót: ${kierunek.skrot}")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        isEditing = true
                        edytowanaNazwa = kierunek.nazwa
                        edytowanySkrot = kierunek.skrot
                    }) {
                        Text("Edytuj")
                    }
                    TextButton(onClick = {
                        viewModel.usunKierunek(kierunek)
                    }) {
                        Text("Usuń")
                    }
                }
            }
        }
    }
}
