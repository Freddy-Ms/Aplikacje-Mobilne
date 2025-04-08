package com.example.intentionsapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.EditText
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
        /*
    private val takePicture =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val image = data?.extras?.get("data") as Bitmap
                imageView.setImageBitmap(image)
            }
        }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDisplaySettings = findViewById<Button>(R.id.btnDisplaySettings)
        val btnMapLocation = findViewById<Button>(R.id.btnMapLocation)
        val btnCamera = findViewById<Button>(R.id.btnCamera)
        val localization = findViewById<EditText>(R.id.EntryLabel)
        imageView = findViewById(R.id.imageView)
        btnDisplaySettings.setOnClickListener {
            try {
                startActivity(Intent(Settings.ACTION_DISPLAY_SETTINGS))
            }catch(e: ActivityNotFoundException){
                Toast.makeText(this, "No Activity", Toast.LENGTH_SHORT).show()
            }
        }

        btnMapLocation.setOnClickListener {
            val userInput = localization.getText().toString()
            val IntentLocalization = Uri.parse("geo:0,0?q=$userInput")
            val mapIntent = Intent(Intent.ACTION_VIEW, IntentLocalization)
            mapIntent.setPackage("com.google.android.apps.maps")
            try{
                startActivity(mapIntent)
            } catch(e: ActivityNotFoundException)
            {
                Toast.makeText(this, "No Activity", Toast.LENGTH_SHORT).show()
            }
        }

        btnCamera.setOnClickListener(View.OnClickListener { v: View? ->
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                startActivityForResult(cameraIntent, pic_id)
            }catch (e: ActivityNotFoundException)
            {
                Toast.makeText(this, "No Activity", Toast.LENGTH_SHORT).show()
            }
        })
       /* btnCamera.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                takePicture.launch(takePictureIntent)
            }catch(e: ActivityNotFoundException){
                Toast.makeText(this, "No Activity", Toast.LENGTH_SHORT).show()
            }
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == pic_id) {
            val photo = data!!.extras!!["data"] as Bitmap?
            imageView.setImageBitmap(photo)
        }
    }
    companion object{
        private const val pic_id = 123
    }
}
