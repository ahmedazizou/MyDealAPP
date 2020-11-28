package com.example.mydeal.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydeal.R
import com.example.mydeal.models.Item
import com.example.mydeal.ui.fragments.ProductsFragment
import com.example.mydeal.utils.GlideLoader
import kotlinx.android.synthetic.main.itemlist_layout.view.*

open class MyItemsListAdapter (
    private val context: Context,
    private val list: ArrayList<Item>,
    private val fragment: ProductsFragment
    ):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.itemlist_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            GlideLoader(context).loadItemPicture(model.image_item,holder.itemView.iv_item_image)
            holder.itemView.tv_item_name.text = model.title
            holder.itemView.tv_item_price.text = "lv ${model.price}"

            holder.itemView.ib_delete_item.setOnClickListener{
                //need to delete item
                fragment.deleteItem(model.item_id)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view)

}





