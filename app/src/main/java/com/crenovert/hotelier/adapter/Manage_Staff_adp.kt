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
import com.crenovert.hotelier.data.Manage_Staff_dt



class Manage_Staff_adp() : RecyclerView.Adapter<ViewHolderMS>() {

    private lateinit var modelList: ArrayList<Manage_Staff_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Manage_Staff_dt, String) -> Unit)

    constructor (
        items: ArrayList<Manage_Staff_dt>,
        context: Context,
        clickListener: (Manage_Staff_dt, String) -> Unit
    ) : this() {
        this.modelList = items
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderMS {
        return ViewHolderMS(
            LayoutInflater.from(context).inflate(R.layout.list_manage_staff, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderMS, position: Int) {


        holder.bind(modelList[position], clickListener)


    }

    // Gets the number of staff in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified staff from model array list at position
    fun removeAt(position: Int) {

        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new staff to model array list
    fun add(item: Manage_Staff_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same  staff inorder by input staff name
    public fun setFilter(newList: ArrayList<Manage_Staff_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }


}

class ViewHolderMS(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btn_edit = itemView.findViewById<Button>(R.id.btnMS_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnMS_delete)

    // Holds the view that will add each staff to
    fun bind(item: Manage_Staff_dt, clickListener: (Manage_Staff_dt, String) -> Unit) {

        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }

        itemView.findViewById<CheckBox>(R.id.cb_manage_staff).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtMS_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtMS_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtMS_nrc).text = item.nrc
        itemView.findViewById<TextView>(R.id.txtMS_ph).text = item.ph
        itemView.findViewById<TextView>(R.id.txtMS_email).text = item.email
        itemView.findViewById<TextView>(R.id.txtMS_address).text = item.address
        itemView.findViewById<TextView>(R.id.txtMS_role).text = item.role
        itemView.findViewById<TextView>(R.id.txtMS_password).text = item.password


        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_manage_staff).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_manage_staff).visibility = View.INVISIBLE
        }
    }


}