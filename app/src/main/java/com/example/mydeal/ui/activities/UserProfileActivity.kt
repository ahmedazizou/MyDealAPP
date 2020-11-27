package com.example.mydeal.ui.activities.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.User
import com.example.mydeal.utils.Constants
import com.example.mydeal.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.et_email
import kotlinx.android.synthetic.main.activity_register.et_first_name
import kotlinx.android.synthetic.main.activity_register.et_last_name
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.io.IOException


class UserProfileActivity : BaseActivity(), View.OnClickListener{

    private lateinit var myUserDetails: User
    private var mySelectedImageFileUri: Uri? = null
    private var myUserProfileImageURL : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            // Get user details from intent as a parcel able extra
            myUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        // User shouldn't be able to change some field firstname,lastname,email
        et_first_name.isEnabled = false
        et_first_name.setText(myUserDetails.firstName)

        et_last_name.isEnabled = false
        et_last_name.setText(myUserDetails.lastName)

        et_email.isEnabled = false
        et_email.setText(myUserDetails.email)


        //add click listener
        iv_user_photo.setOnClickListener(this@UserProfileActivity)

        btn_submit.setOnClickListener(this@UserProfileActivity)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {

                R.id.iv_user_photo -> {

                    // We will check if the permission is already allowed or we need to request for it.
                    // First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed we will request for the same.
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                       // showErrorSnackBar("You already have the storage permission.", false)
                        Constants.shopImagePicker(this)
                    } else {

                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/

                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit -> {
                    if (validateUserProfileCredentials()){

                        showProgressDialog(resources.getString(R.string.loading))

                        //upload image to  cloud
                        //make sure its not empty
                        if (mySelectedImageFileUri != null)
                        FireStoreClass().uploadImagesToCloud(this,mySelectedImageFileUri)
                        else {
                            updateUserProfileDetails()
                        }
                        //showErrorSnackBar("Thank you for updating your profile",false)
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails(){
        //https://www.geeksforgeeks.org/kotlin-hashmap/
        val userHashMap = HashMap<String,Any>()

        val mobileNumber = et_mobile_number.text.toString().trim{it <= ' '}

        val gender = if (rb_male.isChecked){
            Constants.MALE
        } else {
            Constants.FEMALE
        }

        //check if url is not empty then put it as url
        if (myUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = myUserProfileImageURL
        }

        //check the number if it not empty then store it in our user hashmap
        if (mobileNumber.isNotEmpty()){
            userHashMap[Constants.PHONE] = mobileNumber.toLong()
        }
        //key: gender value:male
        userHashMap[Constants.GENDER] = gender

        userHashMap[Constants.PROFILE_STEPS] = 1

        //here we updating the data
        FireStoreClass().updateUserProfileData(this, userHashMap)
    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()

        //make a toast alert
        Toast.makeText(
            this@UserProfileActivity,
            resources.getString(R.string.msg_profile_update_success),
            Toast.LENGTH_SHORT
        ).show()

        //send user from the user prof activity to main
        startActivity(Intent(this@UserProfileActivity, DashboardActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //showErrorSnackBar("The storage permission is granted.", false)
                Constants.shopImagePicker(this)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constants.SELECT_IMAGE_REQUEST_CODE){
                if (data != null){
                    try {
                        //Uri of selected image from phone gallery.
                        mySelectedImageFileUri = data.data!!

                        //iv_user_photo.setImageURI(Uri.parse(selectedImageFileUri.toString()))
                        GlideLoader(this).loadUserImage(mySelectedImageFileUri!!,iv_user_photo)
                    } catch (e: IOException){
                        e.printStackTrace()
                        Toast.makeText(
                            this@UserProfileActivity,
                            resources.getString(R.string.image_selection_failed),
                        Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else if (resultCode == Activity.RESULT_CANCELED){
            // a log is printed when user close  or cancel the image selection
            Log.e("Request Cancelled","Image selection cancelled")
        }
    }
    private fun validateUserProfileCredentials():Boolean {
        return when {
            TextUtils.isEmpty(et_mobile_number.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_phone_number),true)
                false
            }
            else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
        //hideProgressDialog()

        myUserProfileImageURL = imageURL
        updateUserProfileDetails()

    }
}