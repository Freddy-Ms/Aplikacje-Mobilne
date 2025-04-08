package com.example.intentions

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val image = data?.extras?.get("data") as? Bitmap
                imageView.setImageBitmap(image)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDisplaySettings = findViewById<Button>(R.id.btnDisplaySettings)
        val btnMapLocation = findViewById<Button>(R.id.btnMapLocation)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        imageView = findViewById(R.id.imageView)

        btnDisplaySettings.setOnClickListener {
            startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
        }

        btnMapLocation.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:50.0614,19.9366?q=Kraków")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        btnCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePicture.launch(takePictureIntent)
        }
    }
}
