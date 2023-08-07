package com.example.fypfsy.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fypfsy.R
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception


class LetsWin : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val btnGetStarted = findViewById<Button>(R.id.btnGetStarted)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lets_win)
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            Toast.makeText(this, "User is already Logged in!", Toast.LENGTH_SHORT).show()
            redirect("MAIN")
        }

        btnGetStarted.setOnClickListener {
            redirect("LOGIN")
        }
    }

    private fun redirect(name: String) {
        val intent = when (name) {
            "LOGIN" -> Intent(this, LogIn::class.java)
            "MAIN" -> Intent(this, MainActivity::class.java)
            else -> throw Exception("no path exists")
        }
        startActivity(intent)
        finish()
    }
}
