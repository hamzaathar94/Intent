package com.example.intent

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.Gallery
import com.example.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding?=null
    private val GALLERY=1
    private val CAMERA=2
    private val Pick_Image=3
    private val REQUEST_VIDEO_CAPTURE=4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.button?.setOnClickListener {

            takePhotoFromCamera()

        }

        binding?.button2?.setOnClickListener {


            choosePhotoFromGallery()

        }
        binding?.button3?.setOnClickListener {


            val playVideoIntent=Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(playVideoIntent,REQUEST_VIDEO_CAPTURE)

        }

    }
    fun choosePhotoFromGallery(){
        val galleryIntent= Intent(Intent.ACTION_PICK,
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent,GALLERY)
    }

    fun takePhotoFromCamera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== Pick_Image && resultCode== RESULT_OK && data!=null && data.data!=null)
        {

            val photoUri=data.data

            binding?.imageView?.visibility=View.VISIBLE
            binding?.videoView?.visibility=View.GONE

            binding?.imageView?.setImageURI(photoUri)
        }
        else if(requestCode==CAMERA && resultCode== RESULT_OK )
        {

            val imageBitmap=data?.extras?.get("data") as Bitmap

            binding?.imageView?.visibility=View.VISIBLE
            binding?.videoView?.visibility=View.GONE

            binding?.imageView?.setImageBitmap(imageBitmap)
        }
        else if(requestCode==REQUEST_VIDEO_CAPTURE && resultCode== RESULT_OK )
        {


         val videoUri=data?.data
            binding?.videoView?.visibility=View.VISIBLE
            binding?.imageView?.visibility=View.GONE
          binding?.videoView?.setVideoURI(videoUri)
         if (videoUri!=null){
             binding?.videoView?.setVideoURI(videoUri)
             binding?.videoView?.start()
         }


        }

    }

}