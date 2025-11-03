package com.example.calculatorapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView

    private var currentInput: String = "0"
    private var firstOperand: Long? = null
    private var pendingOp: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)
        updateDisplay()

        val digitButtons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (id in digitButtons) {
            findViewById<Button>(id).setOnClickListener {
                val d = (it as Button).text.toString()
                onDigitPressed(d)
            }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorPressed('+') }
        findViewById<Button>(R.id.btnSub).setOnClickListener { onOperatorPressed('-') }
        findViewById<Button>(R.id.btnMul).setOnClickListener { onOperatorPressed('*') }
        findViewById<Button>(R.id.btnDiv).setOnClickListener { onOperatorPressed('/') }

        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsPressed() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { onBackspacePressed() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { onClearEntryPressed() }
        findViewById<Button>(R.id.btnC).setOnClickListener { onClearAllPressed() }
        findViewById<Button>(R.id.btnPlusMinus).setOnClickListener { onPlusMinusPressed() }

        findViewById<Button>(R.id.btnDot).setOnClickListener {
            Toast.makeText(this, "Ứng dụng chỉ xử lý số nguyên", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onDigitPressed(d: String) {
        if (currentInput == "0") {
            currentInput = d
        } else {
            if (currentInput.length < 18) currentInput += d
        }
        updateDisplay()
    }

    private fun onOperatorPressed(op: Char) {
        try {
            val cur = currentInput.toLong()
            if (firstOperand == null) {
                firstOperand = cur
            } else if (pendingOp != null) {
                val res = compute(firstOperand!!, cur, pendingOp!!)
                if (res == null) return
                firstOperand = res
            }
            pendingOp = op
            currentInput = "0"
            updateDisplay()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Số không hợp lệ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onEqualsPressed() {
        if (pendingOp == null || firstOperand == null) return
        try {
            val cur = currentInput.toLong()
            val res = compute(firstOperand!!, cur, pendingOp!!)
            if (res == null) return
            currentInput = res.toString()
            firstOperand = null
            pendingOp = null
            updateDisplay()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Số không hợp lệ", Toast.LENGTH_SHORT).show()
        }
    }

    private fun compute(a: Long, b: Long, op: Char): Long? {
        return try {
            when (op) {
                '+' -> a + b
                '-' -> a - b
                '*' -> a * b
                '/' -> {
                    if (b == 0L) {
                        Toast.makeText(this, "Lỗi: chia cho 0", Toast.LENGTH_SHORT).show()
                        null
                    } else a / b
                }
                else -> null
            }
        } catch (e: ArithmeticException) {
            Toast.makeText(this, "Lỗi toán học", Toast.LENGTH_SHORT).show()
            null
        }
    }

    private fun onBackspacePressed() {
        if (currentInput.length <= 1) {
            currentInput = "0"
        } else {
            currentInput = currentInput.dropLast(1)
            if (currentInput == "-" || currentInput.isEmpty()) currentInput = "0"
        }
        updateDisplay()
    }

    private fun onClearEntryPressed() {
        currentInput = "0"
        updateDisplay()
    }

    private fun onClearAllPressed() {
        currentInput = "0"
        firstOperand = null
        pendingOp = null
        updateDisplay()
    }

    private fun onPlusMinusPressed() {
        if (currentInput == "0") return
        currentInput = if (currentInput.startsWith("-")) {
            currentInput.substring(1)
        } else {
            "-$currentInput"
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        display.text = currentInput
    }
}
