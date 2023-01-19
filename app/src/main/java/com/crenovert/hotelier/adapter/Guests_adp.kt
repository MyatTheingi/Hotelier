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
import com.crenovert.hotelier.data.Guests_dt


class Guests_adp() : RecyclerView.Adapter<ViewHolderG>() {

    private lateinit var modelList: ArrayList<Guests_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Guests_dt, String) -> Unit)

    constructor (
        items: ArrayList<Guests_dt>?,
        context: Context,
        clickListener: (Guests_dt, String) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderG {
        return ViewHolderG(
            LayoutInflater.from(context).inflate(R.layout.list_guests, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderG, position: Int) {

        holder.bind(modelList.get(position), clickListener)


    }

    // Gets the number of guests in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified guest from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new guest to model array list
    fun add(item: Guests_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same guests inorder by input guest name
    public fun setFilter(newList: ArrayList<Guests_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderG(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btn_make_reservation = itemView.findViewById<Button>(R.id.btnG_make_reservation)
    val btn_edit = itemView.findViewById<Button>(R.id.btnG_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnG_delete)

    // Holds the view that will add each guest to
    fun bind(item: Guests_dt, clickListener: (Guests_dt, String) -> Unit) {


        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }
        btn_make_reservation.setOnClickListener { clickListener(item, "make") }

        itemView.findViewById<CheckBox>(R.id.cb_guests).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtG_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtG_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtG_nrc).text = item.nrc
        itemView.findViewById<TextView>(R.id.txtG_email).text = item.email
        itemView.findViewById<TextView>(R.id.txtG_ph).text = item.ph
        itemView.findViewById<TextView>(R.id.txtG_address).text = item.address
        itemView.findViewById<TextView>(R.id.txt_country_name).text = item.country

        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_guests).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_guests).visibility = View.INVISIBLE
        }

    }
}