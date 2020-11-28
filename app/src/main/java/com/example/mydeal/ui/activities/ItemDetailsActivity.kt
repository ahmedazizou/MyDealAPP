package com.example.mydeal.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.Item
import com.example.mydeal.ui.activities.fragments.BaseActivity
import com.example.mydeal.utils.Constants
import com.example.mydeal.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_add_product.toolbar_add_product_activity
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : BaseActivity() {

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

        getItemDetails()
    }
    private fun getItemDetails(){
        showProgressDialog(resources.getString(R.string.loading))
        FireStoreClass().getItemDetails(this,myItemId)
    }

    fun itemDetailsSuccess (item: Item) {
        hideProgressDialog()

        //LOAD IMAGE
        GlideLoader(this@ItemDetailsActivity).loadItemPicture(
            item.image_item,
            iv_item_detail_image
        )
        tv_product_details_title.text = item.title
        tv_product_details_price.text = "lv ${item.price}"
        tv_product_details_description.text = item.descriptor
        tv_product_details_available_quantity.text = item.stock_quantity

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
