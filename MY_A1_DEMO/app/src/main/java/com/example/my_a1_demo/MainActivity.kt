package com.example.my_a1_demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import android.util.Log
import android.widget.Toast
import android.widget.Button

class MainActivity : AppCompatActivity() {
    var isMainLayout = true
    var color = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun teleport(view: View) {
        if (isMainLayout) {
            setContentView(R.layout.activity_main)
        } else {
            setContentView(R.layout.activity_second)
        }
        isMainLayout = !isMainLayout
    }
    fun changeColor(view : View){
        color = color + 1
        val layout = findViewById<ConstraintLayout>(R.id.main)
        val text = findViewById<TextView>(R.id.textView)
        if(color % 5 == 0)
        {
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.blue)) // Alternatywnie layout.setBackgroundColor(COLOR.green)
            text.text = getString(R.string.niebieski)
        }
        if(color % 5 == 1)
        {
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.green))
            text.text = getString(R.string.zielony)
        }
        if(color % 5 == 2)
        {
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
            text.text = getString(R.string.biały)
        }
        if(color % 5 == 3)
        {
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.yellow))
            text.text = getString(R.string.żółty)
        }
        if(color % 5 == 4)
        {
            layout.setBackgroundColor(ContextCompat.getColor(this,R.color.black))
            text.text = getString(R.string.czarny)
        }
    }
    fun logcat(view: View)
    {
        Log.d("Logcat","Kliknięto przycisk Logcat!")
        Toast.makeText(this,"Sprawdź sekcję Logcat!", Toast.LENGTH_SHORT).show()
    }
    fun komunikat(view: View){
        Toast.makeText(this, "Przykładowy komunikat w aplikacji", Toast.LENGTH_SHORT).show()
    }
    fun dissapear(view: View){
        val b1 = findViewById<Button>(R.id.button5)
        val b2 = findViewById<Button>(R.id.button6)
        if(b2.visibility == View.GONE)
        {
            b2.visibility = View.VISIBLE
            b1.visibility = View.GONE
        }else{
            b2.visibility = View.GONE
            b1.visibility = View.VISIBLE
        }
    }
}