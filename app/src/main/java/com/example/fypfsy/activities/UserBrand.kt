package com.example.fypfsy.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.fypfsy.R


class UserBrand : AppCompatActivity() {
        private lateinit var buttonPlayer: Button
        private lateinit var buttonBrand: Button

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_user_brand)

            buttonPlayer = findViewById(R.id.playerName)
            buttonBrand = findViewById(R.id.brandName)

            // Set click listeners for the buttons
            buttonPlayer.setOnClickListener {
                openquiz_item()
            }

            buttonBrand.setOnClickListener {
                openBrandRegistration()
            }
        }

        private fun openquiz_item() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        private fun openBrandRegistration() {
            val intent = Intent(this, BrandRegisteration::class.java)
            startActivity(intent)
        }
    }

