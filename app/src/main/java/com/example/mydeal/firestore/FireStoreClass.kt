package com.example.mydeal.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.ImageHeaderParser
import com.example.mydeal.models.Item
import com.example.mydeal.ui.activities.fragments.LoginActivity
import com.example.mydeal.ui.activities.fragments.RegisterActivity
import com.example.mydeal.ui.activities.fragments.UserProfileActivity
import com.example.mydeal.models.User
import com.example.mydeal.ui.activities.AddProductActivity
import com.example.mydeal.ui.activities.SettingsActivity
import com.example.mydeal.ui.fragments.ProductsFragment
import com.example.mydeal.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.ArrayList


class FireStoreClass {

    private val myFirestore = FirebaseFirestore.getInstance()

    // Create a function to access the Cloud Firestore and create a collection.
    // function to make an entry of the registered user in the FireStore database.
    fun registerUser(activity: RegisterActivity, userInfo: User) {

        // The "users" is collection name. If the collection is already created then it will not create the same one again.
        myFirestore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegisteredSucceeded()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }

    // Function to get the user id of the current logged in user.
    fun getCurrentUserId(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }

        return currentUserId
    }

    fun getUserDetails(activity: Activity) {

        //We pass the collection name from which we wants the data.
        myFirestore.collection(Constants.USERS)
            // The document id to get the Fields of user.
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener { document ->

                Log.i(activity.javaClass.simpleName, document.toString())

                // We have received the document snapshot which is converted into the User Data model object.
                val user = document.toObject(User::class.java)!!

                // We use it in order to save & edit data in it
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.MYDEAL_PREFERENCES,
                    Context.MODE_PRIVATE
                )
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                //to make sure we apply what is edited
                editor.apply()


                // Passing the result to the Login Activity.
                when (activity) {
                    is LoginActivity -> {
                        // Call a function of base activity for transferring the result to it.
                        activity.userLoginSuccess(user)
                    }
                    is SettingsActivity -> {
                        activity.userDetailsSuccess(user)

                    }
                }

            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is LoginActivity -> {
                        activity.hideProgressDialog()
                    }
                    is SettingsActivity ->{
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun updateUserProfileData(activity: Activity,userHashMap: HashMap<String,Any>){

        myFirestore.collection(Constants.USERS).document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity){
                    is UserProfileActivity -> {
                        //Hide the loading
                        activity.userProfileUpdateSuccess()
                    }
                }

            }
            .addOnFailureListener { e->
                when (activity){
                    is UserProfileActivity -> {
                        //Hide the loading
                        activity.hideProgressDialog()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,"Error updating details",e
                )
            }
    }

    // A function to upload the image to the cloud storage.
    fun uploadImagesToCloud(activity: Activity,imageFileURI: Uri?, imageType: String){
        //getting the storage reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            imageType + System.currentTimeMillis() + "."
                    + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        //adding the file to reference
        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is success
                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )

                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl
                    .addOnSuccessListener { uri ->
                        Log.e("Downloadable Image URL", uri.toString())

                        //  Pass the success result to base class.

                        // Here call a function of base activity for transferring the result to it.
                        when (activity) {
                            is UserProfileActivity -> {
                                activity.imageUploadSuccess(uri.toString())
                            }
                            is AddProductActivity -> {
                                // even the same name but they are diffrent
                                activity.imageUploadSuccess(uri.toString())
                            }
                        }

                    }
            }
            .addOnFailureListener { exception ->

                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    exception.message,
                    exception
                )
            }
    }

    fun uploadItemDetails(activity: AddProductActivity, itemInfo: Item){
        myFirestore.collection(Constants.ITEMS)
            .document()
            .set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.itemUploadSuccess()
            }
            .addOnFailureListener { e->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "There is an error uploading the item",
                    e
                )
            }

    }

    fun getProductsList(fragment: Fragment) {
        // The collection name for PRODUCTS
        myFirestore.collection(Constants.ITEMS)
            .whereEqualTo(Constants.USER_ID, getCurrentUserId())
            .get() // Will get the documents snapshots.
            .addOnSuccessListener { document ->

                // Here we get the list of boards in the form of documents.
                Log.e("Items List", document.documents.toString())

                // Here we have created a new instance for Products ArrayList.
                val productsList: ArrayList<Item> = ArrayList()

                // A for loop as per the list of documents to convert them into Products ArrayList.
                for (i in document.documents) {

                    val product = i.toObject(Item::class.java)
                    product!!.item_id = i.id

                    productsList.add(product)
                }

                when (fragment) {
                    is ProductsFragment -> {
                        fragment.successItemsListFromFireStore(productsList)
                    }
                }
            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error based on the base class instance.
                when (fragment) {
                    is ProductsFragment -> {
                        fragment.hideProgressDialog()
                    }
                }
                Log.e("Get Product List", "Error while getting product list.", e)
            }
    }
}