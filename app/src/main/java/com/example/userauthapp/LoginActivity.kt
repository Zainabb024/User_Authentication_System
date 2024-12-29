package com.example.userauthapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

import  com.example.userauthapp.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setupClickListeners()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("LoginActivity", "Initialization Error: ", e)
            Log.e("LoginActivity", "Error Message: ${e.message}")
            Log.e("LoginActivity", "Stack Trace: ${e.stackTraceToString()}")
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()

        }
    }

    private fun setupClickListeners() {
        binding.loginButton.setOnClickListener {
            try {
                val email = binding.emailInput.text.toString()
                val password = binding.passwordInput.text.toString()

                if (email.isEmpty() || password.isEmpty()) {
                    MaterialAlertDialogBuilder(this)
                        .setTitle("Error")
                        .setMessage("Please fill all fields")
                        .setPositiveButton("OK", null)
                        .show()
                    return@setOnClickListener
                }

                performLogin(email, password)
            } catch (e: Exception) {
                Log.e("LoginActivity", "Login Button Click Error: ", e)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Login Error: ${e.message}")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun performLogin(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.login(LoginRequest(email, password))
                withContext(Dispatchers.Main) {
                    // Save token (we'll implement this later)
                    Toast.makeText(this@LoginActivity, "Login successful!", Toast.LENGTH_SHORT).show()
                    // Navigate to main screen (we'll implement this next)
                    SharedPrefManager.saveToken(this@LoginActivity, response.token)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}