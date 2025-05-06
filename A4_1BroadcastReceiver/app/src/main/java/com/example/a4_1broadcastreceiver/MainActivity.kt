package com.example.a4_1broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val powerReceiver = PowerReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_POWER_CONNECTED)
            addAction(Intent.ACTION_POWER_DISCONNECTED)
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_BATTERY_OKAY)
        }
        registerReceiver(powerReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(powerReceiver)
    }

    class PowerReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                Intent.ACTION_POWER_CONNECTED -> {
                    Toast.makeText(context, "Zasilanie podłączone", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_POWER_DISCONNECTED -> {
                    Toast.makeText(context, "Zasilanie odłączone", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_BATTERY_LOW -> {
                    Toast.makeText(context, "Niski poziom baterii!", Toast.LENGTH_SHORT).show()
                }
                Intent.ACTION_BATTERY_OKAY -> {
                    Toast.makeText(context, "Poziom baterii OK", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}