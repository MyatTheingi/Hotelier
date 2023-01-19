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
import com.crenovert.hotelier.data.Bed_Types_dt


class Bed_Types_adp() : RecyclerView.Adapter<ViewHolderBT>() {

    private lateinit var modelList: ArrayList<Bed_Types_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Bed_Types_dt) -> Unit)

    constructor (
        items: ArrayList<Bed_Types_dt>?, context: Context, clickListener: (Bed_Types_dt) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderBT {
        return ViewHolderBT(
            LayoutInflater.from(context).inflate(R.layout.list_bed_types, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderBT, position: Int) {


        holder.bind(modelList[position], clickListener)


    }

    // Gets the number of bed types in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified bed types from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //search the same bed types inorder by input bed type name
    public fun setFilter(newList: ArrayList<Bed_Types_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderBT(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val btn_delete = itemView.findViewById<Button>(R.id.btnBT_delete)

    // Holds the view that will add each bed type to
    fun bind(item: Bed_Types_dt, clickListener: (Bed_Types_dt) -> Unit) {

        btn_delete.setOnClickListener { clickListener(item) }


        itemView.findViewById<CheckBox>(R.id.cb_bed_types).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtBT_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtBT_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtBT_dimen).text = item.dimen
        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_bed_types).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_bed_types).visibility = View.INVISIBLE
        }
    }


}