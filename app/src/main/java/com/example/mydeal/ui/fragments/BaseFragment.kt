package com.example.mydeal.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mydeal.R
import kotlinx.android.synthetic.main.process_progress.*


open class BaseFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var myProgressLoad: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    fun showProgressDialog(text: String) {
        myProgressLoad = Dialog(requireActivity())

        //Set the screen content from a layout resource.
        //The resource will be inflated, adding all top-level views to the screen.
        myProgressLoad.setContentView(R.layout.process_progress)

        myProgressLoad.deal_progress_text.text = text

        myProgressLoad.setCancelable(false)
        myProgressLoad.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        myProgressLoad.show()
    }

    fun hideProgressDialog() {
        myProgressLoad.dismiss()
    }
}