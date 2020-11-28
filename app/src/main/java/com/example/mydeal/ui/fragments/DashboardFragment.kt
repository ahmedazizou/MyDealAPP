package com.example.mydeal.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*


import com.example.mydeal.R
import com.example.mydeal.firestore.FireStoreClass
import com.example.mydeal.models.Item
import com.example.mydeal.ui.activities.SettingsActivity

class DashboardFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //if we need to user option menu in fg we need to use it.
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        getDashboardItemsList()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel =ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when(id){
            R.id.action_settings -> {
                //launch the sitting activity on click of action item
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun successDashboardItemsList(dashboardItemsList: ArrayList<Item>){
        hideProgressDialog()

        for (i in dashboardItemsList) {
            Log.i("item title",i.title)
        }
    }

    private fun getDashboardItemsList(){
        showProgressDialog(resources.getString(R.string.loading))

        FireStoreClass().getDashboardItemList(this@DashboardFragment)
    }
}