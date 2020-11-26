package com.example.mydeal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


//https://medium.com/the-lazy-coders-journal/easy-parcelable-in-kotlin-the-lazy-coders-way-9683122f4c00
//https://plugins.jetbrains.com/plugin/7332-android-parcelable-code-generator
@Parcelize
class User (
    val id: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val profileImage: String = "",
    val phone: Long = 0,
    val gender: String = "",
    val profileSteps: Int = 0
): Parcelable