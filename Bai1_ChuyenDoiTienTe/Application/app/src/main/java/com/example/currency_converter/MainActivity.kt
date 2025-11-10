package com.example.currency_converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var spFrom: Spinner
    private lateinit var spTo: Spinner
    private lateinit var edtFrom: EditText
    private lateinit var edtTo: EditText

    private val currency = arrayOf(
        "USD", "EUR", "JPY", "GBP", "AUD",
        "CAD", "CHF", "CNY", "KRW", "VND"
    )

    private val rate = doubleArrayOf(
        1.0,
        0.92,
        155.0,
        0.79,
        1.52,
        1.37,
        0.91,
        7.12,
        1340.0,
        23400.0
    )

    private var typing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Ánh xạ view
        spFrom = findViewById(R.id.spFrom)
        spTo = findViewById(R.id.spTo)
        edtFrom = findViewById(R.id.edtFrom)
        edtTo = findViewById(R.id.edtTo)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currency)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spFrom.adapter = adapter
        spTo.adapter = adapter

        edtFrom.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (typing) return
                typing = true
                convertMoney()
                typing = false
            }
        })

        val changeListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                convertMoney()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spFrom.onItemSelectedListener = changeListener
        spTo.onItemSelectedListener = changeListener
    }

    private fun convertMoney() {
        val txt = edtFrom.text.toString()
        if (txt.isEmpty()) {
            edtTo.setText("")
            return
        }

        try {
            val value = txt.toDouble()
            val from = spFrom.selectedItemPosition
            val to = spTo.selectedItemPosition

            val result = (value / rate[from]) * rate[to]

            edtTo.setText(String.format(Locale.US, "%.4f", result))
        } catch (e: NumberFormatException) {
            edtTo.setText("")
        }
    }
}