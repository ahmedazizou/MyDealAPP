package com.example.mydeal.utils

//Constants are accessible hole application
object Constants {
    const val READ_STORAGE_PERMISSION_CODE: Int = 2

    //using in order to get users collection from our cloud firestore
    const val USERS: String = "users"
    const val MYDEAL_PREFERENCES: String = "MyDealPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
}