package com.example.mydeal.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.mydeal.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.process_progress.*

// Create a function to show the success and error messages in snack bar component.
open class BaseActivity : AppCompatActivity() {
    private lateinit var mProgressLoad: Dialog

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_base)
//    }
    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity,
                    R.color.colorBarSuccess
                )
            )
        }

        //call this method to show the snack bar type
        snackBar.show()
    }
    //  Create a function to load and show the progress dialog.

    // This function is used to show the progress dialog with the title and message to user.

    fun showProgressDialog(text: String) {
        mProgressLoad = Dialog(this)

        //Set the screen content from a layout resource.
        //The resource will be inflated, adding all top-level views to the screen.
        mProgressLoad.setContentView(R.layout.process_progress)

        mProgressLoad.deal_progress_text.text = text

        mProgressLoad.setCancelable(false)
        mProgressLoad.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        mProgressLoad.show()
    }


    //  Create a function to hide progress dialog.
    // This function is used to dismiss the progress dialog if it is visible to user.

    fun hideProgressDialog() {
        mProgressLoad.dismiss()
    }
    // END
}