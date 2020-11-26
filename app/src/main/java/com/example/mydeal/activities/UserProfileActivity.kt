package com.example.mydeal.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mydeal.R
import com.example.mydeal.models.User
import com.example.mydeal.utils.Constants
import kotlinx.android.synthetic.main.activity_register.*

class UserProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        var userDetails: User = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            // Get user details from intent as a parcel able extra
            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        // User shouldn't be able to change some field firstname,lastname,email
        et_first_name.isEnabled = false
        et_first_name.setText(userDetails.firstName)

        et_last_name.isEnabled = false
        et_last_name.setText(userDetails.lastName)

        et_email.isEnabled = false
        et_email.setText(userDetails.email)

    }


}