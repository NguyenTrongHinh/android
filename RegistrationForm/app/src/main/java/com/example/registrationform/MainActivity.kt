package com.example.registrationform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.registrationform.R

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var rgGender: RadioGroup
    private lateinit var rbMale: RadioButton
    private lateinit var rbFemale: RadioButton
    private lateinit var etBirthday: EditText
    private lateinit var etAddress: EditText
    private lateinit var etEmail: EditText
    private lateinit var cbAgree: CheckBox
    private lateinit var btnSelect: Button
    private lateinit var btnRegister: Button
    private lateinit var calendarView: CalendarView

    private var isCalendarVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        btnSelect.setOnClickListener {
            toggleCalendar()
        }

        calendarView.setOnDateChangeListener { _, year, month, day ->
            etBirthday.setText("$day/${month + 1}/$year")
            calendarView.visibility = View.GONE
            isCalendarVisible = false
        }

        btnRegister.setOnClickListener {
            validate()
        }
    }

    private fun initViews() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        rgGender = findViewById(R.id.rgGender)
        rbMale = findViewById(R.id.rbMale)
        rbFemale = findViewById(R.id.rbFemale)
        etBirthday = findViewById(R.id.etBirthday)
        etAddress = findViewById(R.id.etAddress)
        etEmail = findViewById(R.id.etEmail)
        cbAgree = findViewById(R.id.cbAgree)
        btnSelect = findViewById(R.id.btnSelect)
        btnRegister = findViewById(R.id.btnRegister)
        calendarView = findViewById(R.id.calendarView)
    }

    private fun toggleCalendar() {
        isCalendarVisible = !isCalendarVisible
        calendarView.visibility = if (isCalendarVisible) View.VISIBLE else View.GONE
    }

    private fun validate() {
        var ok = true

        fun checkField(et: EditText): Boolean {
            if (et.text.toString().trim().isEmpty()) {
                et.setBackgroundColor(0xFFFF0000.toInt())
                ok = false
                return false
            } else {
                et.setBackgroundColor(0xFFECECEC.toInt())
                return true
            }
        }

        checkField(etFirstName)
        checkField(etLastName)
        checkField(etBirthday)
        checkField(etAddress)
        checkField(etEmail)

        if (!cbAgree.isChecked) {
            Toast.makeText(this, "You must agree!", Toast.LENGTH_SHORT).show()
            ok = false
        }

        if (ok) {
            Toast.makeText(this, "Registration successful!", Toast.LENGTH_LONG).show()
        }
    }
}
