package pwr.lab.demo_a4_2_compose

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            DemoA42Content()     // <<<<<<<<<<<

            //  Lub pełniejsza wersja uruchomienia tej samej zawartości ( z dodaniem stylu/Theme )
            //  DEMO_A4_2_COMPOSETheme {
            //      Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            //          DemoA42Content()       // <<<<<<<<<<<
            //      }
            //  }

        }
    }
}


@Composable
fun DemoA42Content() {  //funkcja odpowiadająca za cały ekran (zawierający trzy różne "Dema"
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ){
        Text(
            text = "DEMO A4_2 Compose",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        HorizontalDivider(thickness = 30.dp)
        DemoPrzelaczanePrzyciski()
        HorizontalDivider(thickness = 20.dp)
        DemoNapiwek()
        HorizontalDivider(thickness = 20.dp)
        DemoBatteryBroadcast()  // ToDo
    }

}


@Composable
fun DemoPrzelaczanePrzyciski() {
    // zmienne modelu danych -> stanu aplikacji
    var stanPrzyciskow by remember { mutableStateOf(1) }  // 1 - aktywny jest pierwszy przycisk
    // 2 - aktywny jest drugi, itd
    // elementy interfejsu użytkownika
    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "1) DemoPrzyciski",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            // PIERWSZY przycisk, którego kliknięcie aktywuje DRUGI przycisk
            Button(
                onClick = { stanPrzyciskow = 2 },
                enabled = ( stanPrzyciskow == 1 )
                // lub jakas bardziej zlozona logika, np.
                // onClick = { stanPrzyciskow = if (stanPrzyciskow == 2) 3 else 1 },
            ) {
                Text(text = "PIERWSZY")
            }

            // DRUGI przycisk, którego kliknięcie aktywuje TRZECI przycisk
            Button(
                onClick = { stanPrzyciskow = 3 },
                enabled = ( stanPrzyciskow == 2 )
            ) {
                Text(text = "DRUGI")
            }
            // TRZECI przycisk, którego kliknięcie aktywuje PIERWSZY przycisk
            Button(
                onClick = { stanPrzyciskow = 1 },
                enabled = ( stanPrzyciskow == 3 )
            ) {
                Text(text = "TRZECI")
            }

        }
    }
}


@Composable
fun DemoNapiwek() {
    // zmienne modelu danych -> stanu aplikacji
    var tekstKosztuZamowienia by rememberSaveable { mutableStateOf("") }
    var tekstKwotyNapiwku by rememberSaveable { mutableStateOf("---") }
    var tekstWartosciNapiwku by rememberSaveable { mutableStateOf("") }
    // elementy interfejsu użytkownika
    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "2) DemoNapiwek",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = tekstKosztuZamowienia,
            onValueChange = {
                tekstKosztuZamowienia = it
                // jeżeli obliczenia nie są skomplikowane,
                // to można już tutaj policzyć napiwek
                // i ustawić zawartość wynikowej zmiennej "tekstKwotyNapiwku"
                // (wtedy przycisk "POLICZ NAPIWEK" można pominąć)
            },
            placeholder = {Text("Tutaj wpisz koszt zamówienia")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        TextField(
            value = tekstWartosciNapiwku,
            onValueChange = {
                tekstWartosciNapiwku = it
                // jeżeli obliczenia nie są skomplikowane,
                // to można już tutaj policzyć napiwek
                // i ustawić zawartość wynikowej zmiennej "tekstKwotyNapiwku"
                // (wtedy przycisk "POLICZ NAPIWEK" można pominąć)
            },
            placeholder = {Text("Tutaj wpisz wartość % napiwku")},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))

        val context = LocalContext.current  //kontekst do wyswietlenia powiadomienia "Toast"
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val kosztZamowienia = tekstKosztuZamowienia.toDoubleOrNull()
                val wartoscNapiwku = tekstWartosciNapiwku.toDoubleOrNull()
                if( kosztZamowienia!= null && wartoscNapiwku != null) {
                    val wartoscNapiwku = kosztZamowienia * wartoscNapiwku / 100
                    tekstKwotyNapiwku = String.format("%.2f zł", wartoscNapiwku)
                } else {
                    tekstKwotyNapiwku = "---"
                    Toast.makeText(context,"Niepoprawny koszt zamówienia lub napiwku",Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "POLICZ NAPIWEK")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Napiwek: " + tekstKwotyNapiwku,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}


@Composable
fun DemoBatteryBroadcast() {
    // zmienne modelu danych -> stanu aplikacji
    // var ? ? ?
    var statusBateri by remember { mutableStateOf("Unknown") }
    // elementy interfejsu użytkownika
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val batteryReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_BATTERY_OKAY ->{
                        statusBateri ="BATTERY_OK"
                    }
                    Intent.ACTION_BATTERY_LOW -> {
                        statusBateri ="BATTERY_LOW"
                    }
                }
            }
        }

        val filter = IntentFilter().apply{
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
        }
        context.registerReceiver(batteryReceiver, filter)

        onDispose {
            context.unregisterReceiver(batteryReceiver)
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(1f)
    ) {
        Text(
            text = "3) DemoBatteryBroadcast",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Blue
        )
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Stan baterii: $statusBateri",
            fontSize = 20.sp,
            fontStyle = FontStyle.Italic,
            color = Color.LightGray
        )

        /* -------------------------
        Jeżeli wykorzystujesz "Jetpack Compose",
        to zarejestrowanie i wyrejestrowanie komponentu BroadcastReceiver,
        będzie wymagało zdefiniowania "DisposableEffect" oraz "onDispose"

        PRZYKŁAD:
        https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/views-in-compose#case-study-broadcastreceivers

        ---------------------------- */

    }
}


@Preview(showBackground = true)
@Composable
fun DemoA4ContentPreview() {
    DemoA42Content()
}
