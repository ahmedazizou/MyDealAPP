package com.example.mydeal.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        setupActionBar()

        // open the login form after user clicks on login
        deal_login.setOnClickListener {

            // send him back the login page
            onBackPressed()

            // launch register form after user clicks on register.
            // val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        //set on click listener for registration submit button
        btn_register.setOnClickListener {
            registerUser()
        }
    }
    // back button to return previous activity
    private fun setupActionBar(){
        setSupportActionBar(toolbar_register_activity)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
        }
        toolbar_register_activity.setNavigationOnClickListener{ onBackPressed()}
    }


     // A function to validate the entries of a new user.

    private fun validateRegisterCredentials(): Boolean {
        return when {
            // Lambda Expression
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) || et_last_name.length() <= 3 -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(et_confirm_password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            et_password.text.toString().trim { it <= ' ' } != et_confirm_password.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !cb_terms_and_condition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
//                showErrorSnackBar(resources.getString(R.string.register_successful), false)
                true
            }
        }
    }

    // Create a function to register the user with email and password using FirebaseAuth.

    private fun registerUser() {
        // https://firebase.google.com/docs/auth/android/firebaseui
        // Check with validate function if the entries are valid or not.
        if (validateRegisterCredentials()) {
            showProgressDialog(text = "Loading")

            val email: String = et_email.text.toString().trim { it <= ' ' }
            val password: String = et_password.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->


                        //hideProgressDialog() // to hide loading page after task gives result

                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user = User(
                                firebaseUser.uid,
                                et_first_name.text.toString().trim{ it <= ' '},
                                et_last_name.text.toString().trim{ it <= ' '},
                                et_email.text.toString().trim{ it <= ' '}

                            )

                            // To Store it in the cloud
                            FireStoreClass().registerUser(this@RegisterActivity,user)

//                            showErrorSnackBar(
//                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
//                                false
//                            )

                            // to sing the user out
                            //FirebaseAuth.getInstance().signOut()
                            // to finish register activity and back to login activity to login
                            //finish()

                        } else {
                            hideProgressDialog()
                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    })
        }
    }

    // function to display once user is Registered successfully
    fun userRegisteredSucceeded() {

        // hide the load disply
        hideProgressDialog()

        Toast.makeText(
            this@RegisterActivity,resources.getString(R.string.register_successful),Toast.LENGTH_SHORT
        ).show()
    }
}