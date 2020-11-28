package com.example.mydeal.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

//Constants are accessible hole application
object Constants {
    const val READ_STORAGE_PERMISSION_CODE = 2

    //using in order to get users collection from our cloud firestore
    const val USERS: String = "users"
    const val MYDEAL_PREFERENCES: String = "MyDealPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val SELECT_IMAGE_REQUEST_CODE = 1
    const val MALE: String = "Male"
    const val FEMALE: String = "Female"
    const val IMAGE:String= "profileImage"
    const val PHONE:String = "phone"
    const val GENDER:String = "gender"
    const val USER_PROFILE_IMAGE:String = "user_profile_image"
    const val ITEM_IMAGE: String = "Item_Image"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"
    const val PROFILE_STEPS: String = "profileSteps"
    const val ITEMS: String = "Items"
    const val EXTRA_ITEM_ID: String = "extra_item_id"

    const val USER_ID:String = "userId"

    fun shopImagePicker(activity: Activity){
        // an intent for launching the image gallery
        val galleryIntent = Intent(
            Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        // Launch the image selection of photo using our constant code
        activity.startActivityForResult(galleryIntent,SELECT_IMAGE_REQUEST_CODE)
    }

    /**
     * A function to get the image file extension of the selected image file.
     *
     * @param activity Activity reference.
     * @param uri Image file uri.
     */
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
         * MimeTypeMap: Two-way map that maps MIME-types to file extensions and vice versa.
         *
         * getSingleton(): Get the singleton instance of MimeTypeMap.
         *
         * getExtensionFromMimeType: Return the registered extension for the given MIME type.
         *
         * contentResolver.getType: Return the MIME type of the given content URL.
         */
        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }

}