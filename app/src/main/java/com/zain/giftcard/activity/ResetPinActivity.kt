package com.zain.giftcard.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zain.giftcard.R
import com.zain.giftcard.utils.SharedPrefManager


class ResetPinActivity : AppCompatActivity() {

    private lateinit var etUsername: EditText
    private lateinit var tvSecurityQuestion: TextView
    private lateinit var etSecurityAnswer: EditText
    private lateinit var etNewPin: EditText
    private lateinit var btnResetPin: Button
    private lateinit var sharedPrefManager: SharedPrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_pin)


        etUsername = findViewById(R.id.etUsername)
        tvSecurityQuestion = findViewById(R.id.tvSecurityQuestion)
        etSecurityAnswer = findViewById(R.id.etSecurityAnswer)
        etNewPin = findViewById(R.id.etNewPin)
        btnResetPin = findViewById(R.id.btnResetPin)
        sharedPrefManager = SharedPrefManager(this)

        findViewById<Button>(R.id.btnCheckUsername).setOnClickListener {
            val username = etUsername.text.toString()
            if (username == sharedPrefManager.getUsername()) {
                tvSecurityQuestion.text = sharedPrefManager.getSecurityQuestion()
                tvSecurityQuestion.visibility = View.VISIBLE
                etNewPin.visibility = View.VISIBLE
                etSecurityAnswer.visibility = View.VISIBLE
                btnResetPin.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show()
            }
        }

        btnResetPin.setOnClickListener {
            val answer = etSecurityAnswer.text.toString()
            val newPin = etNewPin.text.toString()

            if (answer == sharedPrefManager.getSecurityAnswer()) {
                sharedPrefManager.resetPin(newPin)
                Toast.makeText(this, "PIN Reset Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, UnlockActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Wrong Answer!", Toast.LENGTH_SHORT).show()
            }
            }
        }
}