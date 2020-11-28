package com.example.mydeal.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Item(
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val price: String = "",
    val descriptor: String = "",
    val stock_quantity: String = "",
    val image_item: String = "",
    var item_id: String = "",
):Parcelable