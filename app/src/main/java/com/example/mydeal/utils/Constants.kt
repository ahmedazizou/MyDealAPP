package com.example.mydeal.utils

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore

//Constants are accessible hole application
object Constants {
    const val READ_STORAGE_PERMISSION_CODE: Int = 2

    //using in order to get users collection from our cloud firestore
    const val USERS: String = "users"
    const val MYDEAL_PREFERENCES: String = "MyDealPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val SELECT_IMAGE_REQUEST_CODE: Int = 1
    //male or female

    const val MALE: String = "Male"
    const val FEMALE: String = "Female"


    const val PHONE:String = "phone"
    const val GENDER:String = "gender"

    fun shopImagePicker(activity: Activity){
        // an intent for launching the image gallery
        val galleryIntent = Intent(
            Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launch the image selection of photo using our constant code
        activity.startActivityForResult(galleryIntent,SELECT_IMAGE_REQUEST_CODE)
    }
}