package com.example.userauthapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.userauthapp.databinding.ActivityForgotPasswordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.resetPasswordButton.setOnClickListener {
            val email = binding.emailInput.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performPasswordReset(email)
        }

        binding.backToLoginButton.setOnClickListener {
            finish()
        }
    }

    private fun performPasswordReset(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.api.forgotPassword(mapOf("email" to email))
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ForgotPasswordActivity,
                        response.message,
                        Toast.LENGTH_SHORT).show()
                    finish() // Go back to login screen
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ForgotPasswordActivity,
                        "Password reset failed: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}