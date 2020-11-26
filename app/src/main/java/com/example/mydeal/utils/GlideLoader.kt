package com.example.mydeal.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mydeal.R
import java.io.IOException
import java.net.URI

class GlideLoader(val context: Context) {
    fun loadUserImage(imageURI: Uri, imageView: ImageView) {
        try {
            // load the image in the imageview
            Glide
                .with(context)
                .load(imageURI) // URI OF THE IMAGE
                .centerCrop() //Scale type of image
                .placeholder(R.drawable.profile_pic) // the default place holder if image failed
                .into(imageView) // the view in which the image ll be loaded
        } catch (e: IOException){
        e.printStackTrace()
        }
    }
}