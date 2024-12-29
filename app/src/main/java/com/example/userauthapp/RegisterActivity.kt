package com.example.userauthapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.userauthapp.databinding.ActivityRegisterBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameInput.text.toString()
            val email = binding.emailInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            performRegistration(name, email, password)
        }

        binding.backToLoginButton.setOnClickListener {
            finish()
        }
    }

    private fun performRegistration(name: String, email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val registerRequest = RegisterRequest(email, password, name)
                val response = RetrofitClient.api.register(registerRequest)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity,
                        response.message,
                        Toast.LENGTH_SHORT).show()
                    finish() // Go back to login screen
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity,
                        "Registration failed: ${e.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}