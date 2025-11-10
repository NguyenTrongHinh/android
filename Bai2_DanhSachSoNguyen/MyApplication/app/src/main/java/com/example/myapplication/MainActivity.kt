package com.example.myapplication

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private lateinit var edtNumber: EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var listView: ListView
    private lateinit var tvError: TextView
    private lateinit var adapter: ArrayAdapter<Int>
    private val dataList = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtNumber = findViewById(R.id.edtNumber)
        radioGroup = findViewById(R.id.radioGroup)
        listView = findViewById(R.id.listView)
        tvError = findViewById(R.id.tvError)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList)
        listView.adapter = adapter

        edtNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                processResult()
            }
        })

        radioGroup.setOnCheckedChangeListener { _, _ ->
            processResult()
        }

        processResult()
    }

    private fun processResult() {
        val inputStr = edtNumber.text.toString()

        val n = inputStr.toIntOrNull()
        if (n == null || n < 0) {
            dataList.clear()
            adapter.notifyDataSetChanged()
            showError(true)
            return
        }

        dataList.clear()
        val selectedId = radioGroup.checkedRadioButtonId

        for (i in 0 until n) {
            if (checkNumber(i, selectedId)) {
                dataList.add(i)
            }
        }

        adapter.notifyDataSetChanged()
        showError(dataList.isEmpty())
    }

    private fun showError(isShow: Boolean) {
        if (isShow) {
            tvError.visibility = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            tvError.visibility = View.GONE
            listView.visibility = View.VISIBLE
        }
    }

    private fun checkNumber(i: Int, selectedId: Int): Boolean {
        return when (selectedId) {
            R.id.rbEven -> i % 2 == 0
            R.id.rbOdd -> i % 2 != 0
            R.id.rbSquare -> isSquare(i)
            R.id.rbPrime -> isPrime(i)
            R.id.rbPerfect -> isPerfect(i)
            R.id.rbFibo -> isFibonacci(i)
            else -> false
        }
    }

    private fun isSquare(n: Int): Boolean {
        if (n < 0) return false
        val sqrt = sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }

    private fun isPrime(n: Int): Boolean {
        if (n < 2) return false
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isPerfect(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1
        for (i in 2..sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) {
                sum += i
                if (i * i != n) {
                    sum += n / i
                }
            }
        }
        return sum == n && n != 1
    }

    private fun isFibonacci(n: Int): Boolean {
        if (n < 0) return false
        // Một số là số Fibonacci nếu và chỉ nếu 5*n^2 + 4 hoặc 5*n^2 - 4 là số chính phương
        return isSquare(5 * n * n + 4) || isSquare(5 * n * n - 4)
    }
}