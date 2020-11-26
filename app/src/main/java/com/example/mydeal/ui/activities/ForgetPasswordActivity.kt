package com.example.mydeal.ui.activities.fragments

import android.os.Bundle
import android.widget.Toast
import com.example.mydeal.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_register.*

class ForgetPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        setupActionBar()
    }

    // back button to return previous activity
    private fun setupActionBar(){
        setSupportActionBar(toolbar_forgot_password_activity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        toolbar_forgot_password_activity.setNavigationOnClickListener{ onBackPressed()}

        btn_submit.setOnClickListener{
            val email: String =  et_email_reset.text.toString().trim() {it <= ' '}
            if (email.isEmpty()) {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            } else {
                showProgressDialog(resources.getString(R.string.loading))
                // FireBase is handling the email link for us
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener{ task ->
                        hideProgressDialog()
                        if (task.isSuccessful){
                            // show toast message and finish forget password action
                            Toast.makeText(
                                this@ForgetPasswordActivity,
                                resources.getString(R.string.email_url_sent),
                                Toast.LENGTH_LONG
                            ).show()

                            finish()

                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                }
            }
        }
    }
}