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
import com.crenovert.hotelier.data.Permissions_dt


class Permissions_adp() : RecyclerView.Adapter<ViewHolderP>() {


    private lateinit var modelList: ArrayList<Permissions_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Permissions_dt, String) -> Unit)

    constructor (
        items: ArrayList<Permissions_dt>,
        context: Context,
        clickListener: (Permissions_dt, String) -> Unit
    ) : this() {
        this.modelList = items
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderP {
        return ViewHolderP(
            LayoutInflater.from(context).inflate(R.layout.list_permissions, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderP, position: Int) {

        holder.bind(modelList[position], clickListener)
    }

    // Gets the number of permissions in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified permission from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new permission to model array list
    fun add(item: Permissions_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same assign calendar inorder by input assign calendar name
    public fun setFilter(newList: ArrayList<Permissions_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderP(view: View) : RecyclerView.ViewHolder(view) {
    val btn_edit = itemView.findViewById<Button>(R.id.btnP_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnP_delete)

    // Holds the view that will add each permission to
    fun bind(item: Permissions_dt, clickListener: (Permissions_dt, String) -> Unit) {

        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }


        itemView.findViewById<CheckBox>(R.id.cb_permissions).isChecked = item.checkBox
        itemView.findViewById<CheckBox>(R.id.txtP_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtP_name).text = item.name

        var msg = ""
        for (i in 0 until item.permission.size) {
            msg += if (i == item.permission.size - 1)
                item.permission[i]
            else item.permission[i] + ", "
        }
        itemView.findViewById<TextView>(R.id.txtP_permission).text = msg


        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_permissions).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_permissions).visibility = View.INVISIBLE
        }
    }

}