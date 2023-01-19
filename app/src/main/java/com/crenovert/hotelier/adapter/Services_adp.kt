package com.crenovert.hotelier.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crenovert.hotelier.R
import com.crenovert.hotelier.data.Services_dt


class Services_adp() : RecyclerView.Adapter<ViewHolderS>() {

    private lateinit var modelList: ArrayList<Services_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Services_dt, String) -> Unit)


    constructor (
        items: ArrayList<Services_dt>,
        context: Context,
        clickListener: (Services_dt, String) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderS {
        return ViewHolderS(
            LayoutInflater.from(context).inflate(R.layout.list_services, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderS, position: Int) {

        holder.bind(modelList[position], clickListener)


    }

    // Gets the number of services in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified service from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new service to model array list
    fun add(item: Services_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same  services inorder by input service name
    public fun setFilter(newList: ArrayList<Services_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }


}

class ViewHolderS(view: View) : RecyclerView.ViewHolder(view) {
    val btn_edit = itemView.findViewById<Button>(R.id.btnS_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnS_delete)

    // Holds the view that will add each service to
    fun bind(item: Services_dt, clickListener: (Services_dt, String) -> Unit) {


        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }


        itemView.findViewById<CheckBox>(R.id.cb_services).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtS_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtS_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtS_type).text = item.type
        itemView.findViewById<TextView>(R.id.txtS_price).text = item.price.toString()

        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_services).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_services).visibility = View.INVISIBLE
        }

    }


}