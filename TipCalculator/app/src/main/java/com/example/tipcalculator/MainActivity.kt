package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity


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
        inputPrice.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?){calculateTip()}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        inputCustomerService.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                calculateTip()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                return
            }
        })
        inputTasteFood.setOnRatingBarChangeListener { _, _, _ -> calculateTip() }

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