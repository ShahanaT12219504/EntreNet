package com.example.entrenet

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etNewPassword: EditText = findViewById(R.id.etNewPassword)
        val ivTogglePassword: ImageView = findViewById(R.id.ivTogglePassword)
        val btnReset: Button = findViewById(R.id.btnReset)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        var isPasswordVisible = false

        // Toggle Password Visibility
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etNewPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_on) // Eye open icon
            } else {
                etNewPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_off) // Eye closed icon
            }
            etNewPassword.setSelection(etNewPassword.text.length) // Keep cursor position
        }

        // Reset Password Button Click
        btnReset.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val newPassword = etNewPassword.text.toString().trim()
            val storedEmail = sharedPreferences.getString("email", null)

            if (email.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Enter email and new password", Toast.LENGTH_SHORT).show()
            } else if (email == storedEmail) {
                progressBar.visibility = ProgressBar.VISIBLE

                // Update password
                editor.putString("password", newPassword)
                editor.apply()

                progressBar.visibility = ProgressBar.GONE
                Toast.makeText(this, "Password Reset Successful! Please Login.", Toast.LENGTH_SHORT).show()
                finish() // Close activity
            } else {
                Toast.makeText(this, "Email not found! Please register first.", Toast.LENGTH_SHORT).show()
            }
        }


    }
}