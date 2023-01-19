package com.crenovert.hotelier.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.crenovert.hotelier.R
import com.crenovert.hotelier.data.Rooms_dt


class Rooms_adp() : RecyclerView.Adapter<ViewHolderR>() {


    private lateinit var modelList: ArrayList<Rooms_dt>
    private lateinit var context: Context
    private lateinit var clickListener: ((Rooms_dt, String) -> Unit)

    constructor(
        items: ArrayList<Rooms_dt>?,
        context: Context,
        clickListener: (Rooms_dt, String) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderR {
        return ViewHolderR(LayoutInflater.from(context).inflate(R.layout.list_rooms, parent, false))
    }


    override fun onBindViewHolder(holder: ViewHolderR, position: Int) {

        holder.bind(modelList[position], clickListener)
    }

    // Gets the number of rooms in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified room  from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount)

    }

    //search the same  rooms inorder by input room no
    fun setFilter(newList: ArrayList<Rooms_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderR(view: View) : RecyclerView.ViewHolder(view) {

    val btn_edit = itemView.findViewById<Button>(R.id.btnR_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnR_delete)

    // Holds the view that will add each room  to
    fun bind(item: Rooms_dt, clickListener: (Rooms_dt, String) -> Unit) {


        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }


        itemView.findViewById<CheckBox>(R.id.cb_rooms).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtR_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtR_no).text = item.no
        itemView.findViewById<TextView>(R.id.txtR_type).text = item.type
        itemView.findViewById<TextView>(R.id.txtR_floor).text = item.floor
        itemView.findViewById<TextView>(R.id.txtR_status).text = item.status

        when (item.status) {
            "Inactive" -> {
                itemView.findViewById<TextView>(R.id.txtR_status).setTextColor(Color.RED)
            }
            "Active" -> {
                itemView.findViewById<TextView>(R.id.txtR_status).setTextColor(Color.GREEN)
            }

        }


        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_rooms).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_rooms).visibility = View.INVISIBLE
        }


    }

}