package com.example.mydeal.ui.activities.fragments

import android.app.Dialog
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mydeal.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.process_progress.*

// Create a function to show the success and error messages in snack bar component.
open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPress = false

    private lateinit var myProgressLoad: Dialog

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
        myProgressLoad = Dialog(this)

        //Set the screen content from a layout resource.
        //The resource will be inflated, adding all top-level views to the screen.
        myProgressLoad.setContentView(R.layout.process_progress)

        myProgressLoad.deal_progress_text.text = text

        myProgressLoad.setCancelable(false)
        myProgressLoad.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        myProgressLoad.show()
    }



    // This function is used to dismiss the progress dialog if it is visible to user.

    fun hideProgressDialog() {
        myProgressLoad.dismiss()
    }

    fun doubleBackToExit(){
        if (doubleBackToExitPress){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPress = true

        Toast.makeText(
            this,
            resources.getString(R.string.click_back_again_to_exit),
            Toast.LENGTH_SHORT
        ).show()

        @Suppress("DEPRECATION")
        Handler().postDelayed({ doubleBackToExitPress = false}, 2000)



    }
}