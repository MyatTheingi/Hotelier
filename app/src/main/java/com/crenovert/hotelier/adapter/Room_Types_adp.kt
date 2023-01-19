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
import com.crenovert.hotelier.data.Room_Types_dt


class Room_Types_adp() : RecyclerView.Adapter<ViewHolderRT>() {

    private lateinit var modelList: ArrayList<Room_Types_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Room_Types_dt, String) -> Unit)

    constructor (
        items: ArrayList<Room_Types_dt>,
        context: Context,
        clickListener: (Room_Types_dt, String) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderRT {
        return ViewHolderRT(
            LayoutInflater.from(context).inflate(R.layout.list_room_types, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolderRT, position: Int) {
        holder.bind(modelList[position], clickListener)

    }

    // Gets the number of room types in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified room type from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)
    }

    //add the new room type to model array list
    fun add(item: Room_Types_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same assign calendar inorder by input assign calendar name
    public fun setFilter(newList: ArrayList<Room_Types_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }
}

class ViewHolderRT(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btn_edit = itemView.findViewById<Button>(R.id.btnRT_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnRT_delete)

    // Holds the view that will add each room type to
    fun bind(item: Room_Types_dt, clickListener: (Room_Types_dt, String) -> Unit) {

        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }

        itemView.findViewById<CheckBox>(R.id.cb_room_types).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtRT_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtRT_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtRT_bed_type).text = item.bed_type
        itemView.findViewById<TextView>(R.id.txtRT_features).text = item.features

        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_room_types).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_room_types).visibility = View.INVISIBLE
        }
    }
}