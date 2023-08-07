package com.example.fypfsy.activities

import android.net.Uri

data class ProductData(val brandName: String,
                       val contactPerson: String,
                       val email: String,
                       val phone: String,
                       val productName: String,
                       val productDescription: String,
                       val discount: Int,
                       val validityPeriod: Int,
                       val termsAndConditions: String,
                       val imageUri: Uri
)