package com.example.fypfsy.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.fypfsy.R
import com.google.firebase.auth.FirebaseAuth

class LogIn: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmailAddress: EditText
    private lateinit var etPassword: EditText
    val btnSignUp = findViewById<Button>(R.id.btnLogin)
    val btnLogIn = findViewById<Button>(R.id.btnSignUp)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        auth = FirebaseAuth.getInstance()

        btnLogIn.setOnClickListener {
            login()

        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun login() {
        val email = etEmailAddress.text.toString()
        val password = etPassword.text.toString()


        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and Password can't be Empty", Toast.LENGTH_SHORT)
                .show()
            return
        }


        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, " Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }
}




