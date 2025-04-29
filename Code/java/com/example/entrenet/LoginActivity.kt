package com.example.entrenet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var loginLayout: LinearLayout
    private lateinit var logoLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val ivTogglePassword: ImageView = findViewById(R.id.ivTogglePassword)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val tvForgotPassword: TextView = findViewById(R.id.tvForgotPassword)
        val tvRegister: TextView = findViewById(R.id.tvRegister)
        val lottieView: LottieAnimationView = findViewById(R.id.lottieAnimationView)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        var isPasswordVisible = false

        loginLayout = findViewById(R.id.loginContent)
        logoLayout = findViewById(R.id.logoLoadingLayout)

        // Initially show loading (logo + progress bar)
        loginLayout.visibility = LinearLayout.GONE
        logoLayout.visibility = LinearLayout.VISIBLE

        // Load Lottie animation
        LottieCompositionFactory.fromAsset(this, "login_animation.json")
            .addListener { composition ->
                lottieView.setComposition(composition)
                lottieView.playAnimation()
                lottieView.loop(true)

                // After 1 second, show login content and hide logo layout
                Handler(Looper.getMainLooper()).postDelayed({
                    logoLayout.visibility = LinearLayout.GONE
                    loginLayout.visibility = LinearLayout.VISIBLE
                }, 1000)
            }

        // Toggle password visibility
        ivTogglePassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_on)
            } else {
                etPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                ivTogglePassword.setImageResource(R.drawable.visibility_off)
            }
            etPassword.setSelection(etPassword.text.length)
        }

        // Handle login button click
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val registeredEmail = sharedPreferences.getString("email", null)
            val registeredPassword = sharedPreferences.getString("password", null)

            if (registeredEmail == null || registeredPassword == null) {
                Toast.makeText(this, "No account found. Please register first.", Toast.LENGTH_SHORT).show()
            } else if (email == registeredEmail && password == registeredPassword) {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()

                // Show logo + progress bar (same splash layout)
                setContentView(R.layout.activity_splash)

                // After 2 seconds, move to MainActivity
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }, 2000)
            } else {
                Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show()
            }
        }

        // Redirect to ForgotPasswordActivity
        tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        // Redirect to RegisterActivity
        tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
