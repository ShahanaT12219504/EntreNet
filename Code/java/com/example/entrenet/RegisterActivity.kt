package com.example.entrenet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etFullName: EditText = findViewById(R.id.etFullName) // Get Full Name
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val ivTogglePassword: ImageView = findViewById(R.id.ivTogglePassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        var isPasswordVisible = false

        // Toggle Password Visibility
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_on) // Eye open icon
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_off) // Eye closed icon
            }
            etPassword.setSelection(etPassword.text.length) // Maintain cursor position
        }

        btnRegister.setOnClickListener {
            val fullName = etFullName.text.toString().trim() // Get Full Name
            val newEmail = etEmail.text.toString().trim()
            val newPassword = etPassword.text.toString().trim()

            if (fullName.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Store user details in SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putString("fullName", fullName) // Save Full Name
            editor.putString("email", newEmail)
            editor.putString("password", newPassword)
            editor.apply()

            Toast.makeText(this, "Registration Successful! Please login.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}
