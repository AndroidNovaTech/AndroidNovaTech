package com.zain.giftcard.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.zain.giftcard.R

class UnlockActivity : AppCompatActivity() {

    private lateinit var etPin1: EditText
    private lateinit var etPin2: EditText
    private lateinit var etPin3: EditText
    private lateinit var etPin4: EditText
    private lateinit var btnForgetPin: TextView

    private lateinit var sharedPref: SharedPreferences
    private var savedPin: String = ""

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlock)

        // Initialize views
        etPin1 = findViewById(R.id.etPin1)
        etPin2 = findViewById(R.id.etPin2)
        etPin3 = findViewById(R.id.etPin3)
        etPin4 = findViewById(R.id.etPin4)
        btnForgetPin = findViewById(R.id.btnForgetPin)

        // Load saved PIN
        sharedPref = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        savedPin = sharedPref.getString("PIN", "") ?: ""

        // Setup PIN logic
        setupPinEditTexts()

        // Forget PIN
        btnForgetPin.setOnClickListener {
            startActivity(Intent(this, ResetPinActivity::class.java))
        }

        // Biometric Auth
        biometricPrompt = BiometricPrompt(this, ContextCompat.getMainExecutor(this), object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                startActivity(Intent(this@UnlockActivity, MainActivity::class.java))
                finish()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(this@UnlockActivity, "Biometric authentication failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Log in using your biometric credentials")
            .setNegativeButtonText("Use PIN")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun setupPinEditTexts() {
        setupEditText(etPin1, null, etPin2)
        setupEditText(etPin2, etPin1, etPin3)
        setupEditText(etPin3, etPin2, etPin4)
        setupEditText(etPin4, etPin3, null)
    }

    private fun setupEditText(current: EditText, previous: EditText?, next: EditText?) {
        current.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length == 1) {
                    next?.requestFocus()
                    if (current == etPin4) {
                        checkPin()
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        current.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL) {
                if (current.text.isEmpty()) {
                    previous?.text?.clear()
                    previous?.requestFocus()
                }
            }
            false
        }
    }

    private fun checkPin() {
        val enteredPin = etPin1.text.toString() +
                etPin2.text.toString() +
                etPin3.text.toString() +
                etPin4.text.toString()

        if (enteredPin == savedPin) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            Toast.makeText(this, "Incorrect PIN", Toast.LENGTH_SHORT).show()
            clearPinFields()
        }
    }

    private fun clearPinFields() {
        etPin1.setText("")
        etPin2.setText("")
        etPin3.setText("")
        etPin4.setText("")
        etPin1.requestFocus()
    }
}
