package com.example.mydeal.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.example.mydeal.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


//Replace the AppCompatActivity() with BaseActivity Implement the View.OnClickListener and assign the onclick events of respective components in the onClick function.
class LoginActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this clas
        setContentView(R.layout.activity_login)

        // This is used as a full screen operation to cover the status bar during the login action.
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        // Assign the click listener to the views which we have implemented.

        // Click event assigned to Forgot Password text.
        deal_forgot_password.setOnClickListener(this)

        // Click event assigned to Login button.
        btn_login.setOnClickListener(this)

        // Click event assigned to Register text.
        deal_register.setOnClickListener(this)

//        deal_register.setOnClickListener {
//            // launch register form after user clicks on register.
//            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
//            startActivity(intent)
//        }
    }

    // Implement the View.OnClickListener and assign the onclick events of respective components in the onClick function.
    // In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {

                // Click event assigned to Forgot Password text.
                R.id.deal_forgot_password -> {
                    val intent = Intent(this@LoginActivity, ForgetPasswordActivity::class.java)
                    startActivity(intent)
                }
                // Click event assigned to Login button.
                R.id.btn_login -> {

                    // log the user in
                     logInUser()
                    // Call the validate function.
                    // validateLoginCredentials()

                }

                // Click event assigned to Register text
                R.id.deal_register -> {
                    // Launch the register screen when the user clicks on the text.
                    val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    // a function to validate the login details.

    private fun validateLoginCredentials(): Boolean {
        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {
//                showErrorSnackBar("You have been logged in successfully.", false)
                true
            }
        }
    }

    private fun logInUser(){
        if (validateLoginCredentials()){
            //show the load progress.
            showProgressDialog(resources.getString(R.string.loading))

            // Get the input from the user nad check trim space
            val email = et_email.text.toString().trim() {it <= ' ' }
            val password = et_password.text.toString().trim() {it <= ' ' }

            // login using FireBaseAuth
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{ task ->

                    hideProgressDialog() // to hide loading page after task gives result

                    if (task.isSuccessful){
                        // Send user to main activity
                        showErrorSnackBar("You have been logged in successfully.", false)
                    } else {
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
        }
    }


}