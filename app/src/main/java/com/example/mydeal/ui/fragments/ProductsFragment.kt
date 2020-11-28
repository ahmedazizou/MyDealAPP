package com.example.mydeal.ui.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.Item
import com.example.mydeal.ui.activities.AddProductActivity
import com.example.mydeal.ui.adapters.MyItemsListAdapter
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if we need to user option menu in fg we need to use it.
        setHasOptionsMenu(true)
    }

    fun deleteItem(itemId: String) {
        AlertToDeleteItem(itemId)
    }

    private fun getItemsListFromFireStore(){
        showProgressDialog(resources.getString(R.string.loading))
        FireStoreClass().getProductsList(this)
    }

    private fun AlertToDeleteItem(itemId: String){
        val builder = AlertDialog.Builder(requireActivity())
        //set title and message for alert
        builder.setTitle(resources.getString(R.string.delete_dialog))
        builder.setMessage(resources.getString(R.string.delete_dialog_msg))
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        // performing positive action
        builder.setPositiveButton(resources.getString(R.string.yes)) {
            dialogInterface, _ ->
            showProgressDialog(resources.getString(R.string.loading))

            FireStoreClass().deleteItem(this,itemId)
            dialogInterface.dismiss()
        }
        // for negative action
        builder.setNegativeButton(resources.getString(R.string.no)) {
                dialogInterface, _ ->

            dialogInterface.dismiss()
        }
        // Create the alert dialog
        val alertDialog : AlertDialog = builder.create()
        //set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()


    }

    override fun onResume() {
        super.onResume()
        getItemsListFromFireStore()
    }

    fun successItemsListFromFireStore(productsList: ArrayList<Item>) {
        hideProgressDialog()

        if (productsList.size >0){
            rv_my_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_items.layoutManager = LinearLayoutManager(activity)
            rv_my_items.setHasFixedSize(true)
            val adapterItems = MyItemsListAdapter(requireActivity(),productsList,this )
            rv_my_items.adapter = adapterItems
        }else{
            rv_my_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }



    fun itemDeleteSuccess(){
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.item_deleted),
            Toast.LENGTH_SHORT
        ).show()

        //get list items after delete one to refresh
        getItemsListFromFireStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel =ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_menu_product, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id= item.itemId
        when(id){
            R.id.action_add_product -> {
                //launch the sitting activity on click of action item
                startActivity(Intent(activity, AddProductActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}