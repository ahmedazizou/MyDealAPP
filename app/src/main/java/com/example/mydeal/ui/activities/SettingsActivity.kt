package com.example.mydeal.ui.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.User
import com.example.mydeal.ui.activities.fragments.BaseActivity
import com.example.mydeal.ui.activities.fragments.LoginActivity
import com.example.mydeal.ui.activities.fragments.UserProfileActivity
import com.example.mydeal.utils.Constants
import com.example.mydeal.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_setting.*

//Replace the AppCompactActivity with BaseActivity.
class SettingsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var myUserDetails: User
    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_setting)

        // Call the function to setup action bar.
        setupActionBar()

        //Add on click listener
        tv_edit.setOnClickListener(this)
        btn_logout.setOnClickListener(this)

    }

    //  Override the onResume function and call the getUserDetails function init.
    // https://developer.android.com/guide/components/activities/activity-lifecycle
    override fun onResume() {
        super.onResume()

        getUserDetails()
    }


    //  Create a function to setup action bar.
    // START
    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_settings_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_settings_activity.setNavigationOnClickListener { onBackPressed() }
    }


    // Create a function to get the user details from firestore.

    /**
     * A function to get the user details from firestore.
     */
    private fun getUserDetails() {

        // Show the progress dialog
        showProgressDialog(resources.getString(R.string.loading))

        // Call the function of Firestore class to get the user details from firestore which is already created.
        FireStoreClass().getUserDetails(this@SettingsActivity)
    }


    //  Create a function to receive the success result.

    /**
     * A function to receive the user details and populate it in the UI.
     */
    fun userDetailsSuccess(user: User) {
        myUserDetails = user

        // Set the user details to UI.

        // Hide the progress dialog
        hideProgressDialog()

        // Load the image using the Glide Loader class.
        GlideLoader(this@SettingsActivity).loadUserImage(user.profileImage, iv_user_photo)

        tv_name.text = "${user.firstName} ${user.lastName}"
        tv_gender.text = user.gender
        tv_email.text = user.email
        tv_mobile_number.text = "${user.phone}"

    }

    //Override the onClick function.
    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {

                // Call the User Profile Activity to add the Edit Profile feature to the app. Pass the user details through intent.

                R.id.tv_edit -> {
                    val intent = Intent(this@SettingsActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS, myUserDetails)
                    startActivity(intent)
                }


                // Add Logout feature when user clicks on logout button.

                R.id.btn_logout -> {

                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

            }
        }
    }

}