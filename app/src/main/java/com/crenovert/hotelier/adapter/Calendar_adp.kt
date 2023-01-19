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
import com.crenovert.hotelier.data.Calendar_dt


class Calendar_adp() : RecyclerView.Adapter<ViewHolderC>() {

    private lateinit var modelList: ArrayList<Calendar_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Calendar_dt, String) -> Unit)

    constructor (
        items: ArrayList<Calendar_dt>,
        context: Context,
        clickListener: (Calendar_dt, String) -> Unit
    ) : this() {
        this.modelList = items
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderC {
        return ViewHolderC(
            LayoutInflater.from(context).inflate(R.layout.list_calendar, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderC, position: Int) {

        holder.bind(modelList[position], clickListener)


    }

    // Gets the number of calendar in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified calendar from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new calendar to model array list
    fun add(item: Calendar_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same  calendar inorder by input  calendar name
    public fun setFilter(newList: ArrayList<Calendar_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderC(view: View) : RecyclerView.ViewHolder(view) {

    val btn_edit = itemView.findViewById<Button>(R.id.btnC_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnC_delete)

    // Holds the view that will add each calendar to
    fun bind(item: Calendar_dt, clickListener: (Calendar_dt, String) -> Unit) {

        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }

        itemView.findViewById<CheckBox>(R.id.cb_calendar).isChecked = item.checkBox
        itemView.findViewById<CheckBox>(R.id.txtC_id).text = item.id.toString()
        itemView.findViewById<CheckBox>(R.id.txtC_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtC_type).text = item.type
        itemView.findViewById<TextView>(R.id.txtC_price).text = item.price.toString()

        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_calendar).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_calendar).visibility = View.INVISIBLE
        }

    }


}