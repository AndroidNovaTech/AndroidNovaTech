package com.zain.giftcard.activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.zain.giftcard.R
import com.zain.giftcard.utils.SharedPrefManager

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var sharedPrefManager: SharedPrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        sharedPrefManager = SharedPrefManager(this)

        Handler(Looper.getMainLooper()).postDelayed({
            proceedAfterSplash()
        }, 4000) // 2 second splash
    }

    private fun proceedAfterSplash() {
        val pin = sharedPrefManager.getPin()

        if (pin.isNullOrEmpty()) {
            // PIN not set yet → Go to LockActivity (Set PIN)
            startActivity(Intent(this, LockActivity::class.java))
            finish()
        } else if (!sharedPrefManager.isDisclaimerCompleted()) {
            // PIN is set but disclaimer not accepted → Show disclaimer dialog
            showDisclaimerDialog()
        } else {
            // Everything done → Go to UnlockActivity
            startActivity(Intent(this, UnlockActivity::class.java))
            finish()
        }
    }

    private fun showDisclaimerDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_disclamer, null)
        val checkBox = dialogView.findViewById<CheckBox>(R.id.checkboxAgree)
        val agreeButton = dialogView.findViewById<Button>(R.id.btnAgree)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.window?.setBackgroundDrawableResource(R.color.dim)

        agreeButton.isEnabled = false
        agreeButton.setBackgroundColor(ContextCompat.getColor(this, R.color.dim))

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            agreeButton.isEnabled = isChecked
            val color = if (isChecked) R.color.errorColor else R.color.dim
            agreeButton.setBackgroundTintList(ContextCompat.getColorStateList(this, color))
        }

        agreeButton.setOnClickListener {
            sharedPrefManager.setDisclaimerCompleted() // ✅ Save flag
            dialog.dismiss()
            startActivity(Intent(this, UnlockActivity::class.java))
            finish()
        }

        dialog.show()
    }
}
