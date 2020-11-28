package com.example.mydeal.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.mydeal.R
import java.io.IOException
import java.net.URI

class GlideLoader(val context: Context) {
    fun loadUserImage(profileImage: Any, imageView: ImageView) {
        try {
            // load the image in the imageview
            Glide
                .with(context)
                .load(profileImage) // URI OF THE IMAGE
                .centerCrop() //Scale type of image
                .placeholder(R.drawable.profile_pic) // the default place holder if image failed
                .into(imageView) // the view in which the image ll be loaded
        } catch (e: IOException){
        e.printStackTrace()
        }
    }

    fun loadItemPicture(image: Any, imageView: ImageView) {
        try {
            // Load the user image in the ImageView.
            Glide
                .with(context)
                .load(image) // Uri or URL of the image
                .centerCrop() // Scale type of the image.
                .into(imageView) // the view in which the image will be loaded.
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

}