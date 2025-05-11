package com.zain.giftcard.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.zain.giftcard.R
import com.zain.giftcard.utils.SharedPrefManager


class LockActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPin: EditText
    private lateinit var etConfirmPin: EditText
    private lateinit var etSecurityAnswer: EditText
    private lateinit var spinnerSecurityQuestion: Spinner
    private lateinit var btnSetPin: Button
    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        etUsername = findViewById(R.id.etUsername)
        etPin = findViewById(R.id.etPin)
        etConfirmPin = findViewById(R.id.etConfirmPin)
        etSecurityAnswer = findViewById(R.id.etSecurityAnswer)
        spinnerSecurityQuestion = findViewById(R.id.spinnerSecurityQuestion) // ✅ Corrected ID
        btnSetPin = findViewById(R.id.btnSetPin)
        sharedPrefManager = SharedPrefManager(this)

        // Security Questions List
        val questions = arrayOf(
            "Your first pet's name?",
            "Your mother's maiden name?",
            "Your favorite teacher's name?",
            "The city where you were born?"
        )

        // Custom Adapter with Text Color Change
        val adapter = ArrayAdapter(this, R.layout.spinner_item, questions)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        spinnerSecurityQuestion.adapter = adapter

        btnSetPin.setOnClickListener {
            val username = etUsername.text.toString()
            val pin = etPin.text.toString()
            val confirmPin = etConfirmPin.text.toString()
            val securityQuestion = spinnerSecurityQuestion.selectedItem.toString()
            val securityAnswer = etSecurityAnswer.text.toString()

            if (username.isEmpty() || pin.isEmpty() || confirmPin.isEmpty() || securityAnswer.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pin != confirmPin) {
                Toast.makeText(this, "PINs do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            sharedPrefManager.saveCredentials(username, pin, securityQuestion, securityAnswer)
            Toast.makeText(this, "PIN Set Successfully", Toast.LENGTH_SHORT).show()

            startActivity(Intent(this, UnlockActivity::class.java))
            finish()
            }
        }
}