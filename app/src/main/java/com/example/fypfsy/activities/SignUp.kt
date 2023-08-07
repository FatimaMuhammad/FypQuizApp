package com.example.fypfsy.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fypfsy.R
import com.example.fypfsy.activities.MainActivity.*
import com.google.firebase.auth.FirebaseAuth


class SignUp : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText
    private lateinit var confirmPassword: EditText
    val btnSignUp = findViewById<Button>(R.id.btnSignUp)
    val btnLogIn = findViewById<Button>(R.id.btnLogin)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()

        btnSignUp.setOnClickListener {
            signupUser()

        }
        btnLogIn.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun signupUser() {
        val email = etEmailAddress.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = confirmPassword.text.toString()

        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and password can't be blank", Toast.LENGTH_LONG).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "password and confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            return

        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Login failed.", Toast.LENGTH_SHORT).show()
                }

            }

    }
}
