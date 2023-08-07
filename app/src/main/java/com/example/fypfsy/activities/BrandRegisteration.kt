package com.example.fypfsy.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fypfsy.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class BrandRegisteration : AppCompatActivity() {

    private lateinit var brandNameEditText: EditText
    private lateinit var contactPersonEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var productNameEditText: EditText
    private lateinit var productDescriptionEditText: EditText
    private lateinit var discountEditText: EditText
    private lateinit var validityPeriodEditText: EditText
    private lateinit var termsAndConditionsEditText: EditText
    private lateinit var uploadImageButton: Button
    private lateinit var confirmRegistrationButton: Button
    private lateinit var storage: FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var imageUri: Uri


    private val imagePickRequestCode = 100
    private val galleryPermissionRequestCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_brand_registeration)
        // Initialize Firebase
        storage = FirebaseStorage.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        // Find views by their ids
        brandNameEditText = findViewById(R.id.brandName)
        contactPersonEditText = findViewById(R.id.contactPerson)
        emailEditText = findViewById(R.id.etText)
        phoneEditText = findViewById(R.id.etPhone)
        productNameEditText = findViewById(R.id.etProductName)
        productDescriptionEditText = findViewById(R.id.etProductDescription)
        discountEditText = findViewById(R.id.etDiscount)
        validityPeriodEditText = findViewById(R.id.etValidityPeriod)
        termsAndConditionsEditText = findViewById(R.id.etTermsAndConditions)

        uploadImageButton = findViewById(R.id.btnUploadImage)
        confirmRegistrationButton = findViewById(R.id.btnSaved)

        // Set click listeners
        uploadImageButton.setOnClickListener { checkGalleryPermission()}
        confirmRegistrationButton.setOnClickListener { registerBrand() }
    }

    private fun checkGalleryPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                galleryPermissionRequestCode
            )
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, imagePickRequestCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == galleryPermissionRequestCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Toast.makeText(
                    this,
                    "Gallery permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    private fun uploadImageToStorage(productData: ProductData) {
        val storageRef: StorageReference =
            storage.reference.child("products/${productData.brandName}_${System.currentTimeMillis()}")
        storageRef.putFile(productData.imageUri)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    // Save the product details with the download URL to Firestore
                    val product = hashMapOf(
                        "brandName" to productData.brandName,
                        "contactPerson" to productData.contactPerson,
                        "email" to productData.email,
                        "phone" to productData.phone,
                        "productName" to productData.productName,
                        "productDescription" to productData.productDescription,
                        "discount" to productData.discount,
                        "validityPeriod" to productData.validityPeriod,
                        "termsAndConditions" to productData.termsAndConditions,
                        "imageUri" to downloadUri.toString()
                    )

                    firestore.collection("products")
                        .add(product)
                        .addOnSuccessListener {
                            Toast.makeText(
                                this@BrandRegisteration,
                                "Product registered successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                this@BrandRegisteration,
                                "Failed to register product.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
            }
            .addOnFailureListener {
                Toast.makeText(this@BrandRegisteration, "Image upload failed.", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.data!!
        }
    }

    companion object {
        private const val REQUEST_IMAGE_PICK = 1
    }

    private fun registerBrand() {
        val brandName = brandNameEditText.text.toString()
        val contactPerson = contactPersonEditText.text.toString()
        val email = emailEditText.text.toString()
        val phone = phoneEditText.text.toString()
        val productName = productNameEditText.text.toString()
        val productDescription = productDescriptionEditText.text.toString()
        val discount = discountEditText.text.toString()
        val validityPeriod = validityPeriodEditText.text.toString()
        val termsAndConditions = termsAndConditionsEditText.text.toString()

        if (brandName.isEmpty() || contactPerson.isEmpty() || email.isEmpty() || phone.isEmpty() || productName.isEmpty() || productDescription.isEmpty() || discount.isEmpty() || validityPeriod.isEmpty() || termsAndConditions.isEmpty() || imageUri == null) {
            Toast.makeText(this, "Please fill all the details and upload an image.", Toast.LENGTH_SHORT).show()
            return
        }

        val productData = ProductData(
            brandName, contactPerson, email, phone, productName, productDescription,
            discount.toInt(), validityPeriod.toInt(), termsAndConditions, imageUri
        )

        // Pass the productData object to uploadImageToStorage
        uploadImageToStorage(productData)

         firestore.collection("brands")
            .add(productData)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "Brand registration successful",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .addOnFailureListener {
                Toast.makeText(
                    this,
                    "Brand registration failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
