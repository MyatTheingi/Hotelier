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
import com.crenovert.hotelier.data.Walkin_Reservations_dt


class Walkin_Reservations_adp() : RecyclerView.Adapter<ViewHolderWR>() {


    private lateinit var modelList: ArrayList<Walkin_Reservations_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Walkin_Reservations_dt, String) -> Unit)


    constructor (
        items: ArrayList<Walkin_Reservations_dt>,
        context: Context,
        clickListener: (Walkin_Reservations_dt, String) -> Unit
    ) : this() {
        this.modelList = items!!
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderWR {
        return ViewHolderWR(
            LayoutInflater.from(context).inflate(R.layout.list_walkin_reservations, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderWR, position: Int) {


        holder.bind(modelList[position], clickListener)


    }

    // Gets the number of walkin reservations in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    // delete the specified reservation from model array list at position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //add the new reservation to model array list
    fun add(item: Walkin_Reservations_dt) {
        modelList.add(item)
        notifyDataSetChanged()
    }

    //search the same  reservations inorder by input reservation id
    public fun setFilter(newList: ArrayList<Walkin_Reservations_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }


}

class ViewHolderWR(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val btn_edit = itemView.findViewById<Button>(R.id.btnWR_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnWR_delete)
    val txt_status = itemView.findViewById<TextView>(R.id.txtWR_status)

    // Holds the view that will add each reservation to
    fun bind(
        item: Walkin_Reservations_dt,
        clickListener: (Walkin_Reservations_dt, String) -> Unit
    ) {


        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }


        itemView.findViewById<CheckBox>(R.id.cb_walkin_reservation).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtWR_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtWR_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtWR_nrc).text = item.nrc
        itemView.findViewById<TextView>(R.id.txtWR_ph).text = item.ph
        itemView.findViewById<TextView>(R.id.txtWR_email).text = item.email
        itemView.findViewById<TextView>(R.id.txtWR_address).text = item.address
        itemView.findViewById<TextView>(R.id.txtWR_checkin).text = item.checkin
        itemView.findViewById<TextView>(R.id.txtWR_checkout).text = item.checkout
        itemView.findViewById<TextView>(R.id.txtWR_total).text = item.total.toString()
        itemView.findViewById<TextView>(R.id.txtWR_paid).text = item.paid.toString()
        itemView.findViewById<TextView>(R.id.txtWR_balance).text = item.balance.toString()
        itemView.findViewById<TextView>(R.id.txtWR_bookingtime).text = item.bookingtime
        itemView.findViewById<TextView>(R.id.txtWR_status).text = item.status

        when (item.status) {
            "Check In" -> {
                txt_status.setTextColor(Color.GREEN)
            }
            "New" -> {
                txt_status.setTextColor(Color.BLUE)
            }
            "Check Out" -> {
                txt_status.setTextColor(Color.RED)
            }

        }

        if (item.checkBox) {
            itemView.findViewById<CheckBox>(R.id.cb_walkin_reservation).visibility = View.VISIBLE
        } else {
            itemView.findViewById<CheckBox>(R.id.cb_walkin_reservation).visibility = View.INVISIBLE
        }

    }


}