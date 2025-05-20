package com.example.kierunkistudiowcompose
import androidx.lifecycle.ViewModel
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
class KierunkiViewModel : ViewModel() {
    private val _listaKierunkow = mutableStateListOf(
        DaneKierunku(1, "Cyberbezpieczeństwo", "CBE"),
        DaneKierunku(2,"Informatyczne Systemy Automatyki", "ISA")
    )
    val listaKierunkow: List<DaneKierunku> = _listaKierunkow

    fun dodajKierunek(nazwa: String, skrot: String){
        val nowyId = (_listaKierunkow.maxOfOrNull {it.id} ?:0) + 1
        _listaKierunkow.add(DaneKierunku(nowyId,nazwa,skrot))
    }

    fun usunKierunek(kierunek: DaneKierunku) {
        _listaKierunkow.remove(kierunek)
    }

    fun aktualizujKierunek(id: Int, nowaNazwa: String, nowySkrot: String){
        val index = _listaKierunkow.indexOfFirst { it.id == id }
        if (index >= 0) {
            _listaKierunkow[index] = _listaKierunkow[index].copy(nazwa = nowaNazwa, skrot = nowySkrot)
        }
    }

    fun sortujKierunki(){
        _listaKierunkow.sortBy { it.nazwa }
    }

    fun wyszukajKierunki(query: String): List<DaneKierunku>{
        return _listaKierunkow.filter { it.nazwa.contains(query, ignoreCase = true) }
    }
}