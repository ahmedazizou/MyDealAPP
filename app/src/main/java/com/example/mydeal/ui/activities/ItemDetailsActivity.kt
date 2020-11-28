package com.example.mydeal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mydeal.R
import com.example.mydeal.utils.Constants
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.toolbar_add_product_activity
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity() {

    private var myItemId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)
        setupActionBar()

        if (intent.hasExtra(Constants.EXTRA_ITEM_ID)){
            myItemId = intent.getStringExtra(Constants.EXTRA_ITEM_ID)!!

            // test
            Log.i("Item ID", myItemId)
        }
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_item_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_item_details_activity.setNavigationOnClickListener { onBackPressed() }
    }
}
