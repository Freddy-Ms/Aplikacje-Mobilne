package com.example.napiwek

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.*

class MainActivity : AppCompatActivity() {
    private lateinit var inputPrice: EditText
    private lateinit var inputCustomerService: Spinner
    private lateinit var inputTasteFood: RatingBar
    private lateinit var textPrice: TextView
    private lateinit var textTipPercentage: TextView
    private lateinit var textTipAmount: TextView
    private lateinit var textTotal: TextView
    private lateinit var countTip: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        inputPrice = findViewById(R.id.inputPrice)
        inputCustomerService = findViewById(R.id.inputCustomerService)
        inputTasteFood = findViewById(R.id.inputTasteFood)
        textPrice = findViewById(R.id.textPrice)
        textTipPercentage = findViewById(R.id.textTipPercentage)
        textTipAmount = findViewById(R.id.textTipAmount)
        textTotal = findViewById(R.id.textTotal)
        countTip = findViewById(R.id.countTip)
        countTip.setOnClickListener { calculateTip() }
    }
    private fun calculateTip() {
        val priceText = inputPrice.text.toString()
        if (priceText.isEmpty()) {
            inputPrice.error = "Enter a valid price"
            return
        }

        val price = priceText.toDouble()
        val baseTip = 5.0

        val selectedService = inputCustomerService.selectedItem.toString()
        val serviceMultiplier = when (selectedService) {
            "Excellent" -> 2.0
            "Good" -> 1.5
            "Average" -> 1.0
            "Poor" -> 0.5
            "Terrible" -> 0.2
            else -> 1.0
        }

        val foodRating = inputTasteFood.rating.toInt() // Each star +1%
        val finalTipPercentage = baseTip * serviceMultiplier + foodRating

        val tipAmount = price * (finalTipPercentage / 100)
        val total = price + tipAmount

        textPrice.text = "Price of the order: %.2f zł".format(price)
        textTipPercentage.text = "Tip: %.2f%%".format(finalTipPercentage)
        textTipAmount.text = "Tip amount: %.2f zł".format(tipAmount)
        textTotal.text = "Total: %.2f zł".format(total)
    }
}