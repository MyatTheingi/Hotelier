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
import com.crenovert.hotelier.data.Assign_Calendar_dt


class Assign_Calendar_adp() : RecyclerView.Adapter<ViewHolderAC>() {

    private lateinit var modelList: ArrayList<Assign_Calendar_dt>
    private var context: Context? = null
    private lateinit var clickListener: ((Assign_Calendar_dt, String) -> Unit)

    constructor (
        items: ArrayList<Assign_Calendar_dt>,
        context: Context,
        clickListener: (Assign_Calendar_dt, String) -> Unit
    ) : this() {
        this.modelList = items
        this.context = context
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderAC {
        return ViewHolderAC(
            LayoutInflater.from(context).inflate(R.layout.list_view_assign_calendar, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolderAC, position: Int) {
        holder.bind(modelList[position], clickListener)
    }

    // Gets the number of assign calendar in the list
    override fun getItemCount(): Int {
        return modelList.size
    }

    //remove the specified assign calendar at given position
    fun removeAt(position: Int) {
        modelList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, modelList.size)

    }

    //search the same assign calendar inorder by input assign calendar name
    public fun setFilter(newList: ArrayList<Assign_Calendar_dt>) {
        modelList = ArrayList()
        modelList.addAll(newList)
        notifyDataSetChanged()
    }

}

class ViewHolderAC(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val btn_edit = itemView.findViewById<Button>(R.id.btnAC_edit)
    val btn_delete = itemView.findViewById<Button>(R.id.btnAC_delete)

    // Holds the view that will add each assign_calendar to
    fun bind(item: Assign_Calendar_dt, clickListener: (Assign_Calendar_dt, String) -> Unit) {


        btn_edit.setOnClickListener { clickListener(item, "edit") }
        btn_delete.setOnClickListener { clickListener(item, "delete") }


        itemView.findViewById<CheckBox>(R.id.cb_assign_calendar).isChecked = item.checkBox
        itemView.findViewById<TextView>(R.id.txtAC_id).text = item.id.toString()
        itemView.findViewById<TextView>(R.id.txtAC_name).text = item.name
        itemView.findViewById<TextView>(R.id.txtAC_type).text = item.type
        itemView.findViewById<TextView>(R.id.txtAC_from).text = item.from
        itemView.findViewById<TextView>(R.id.txtAC_to).text = item.to
        itemView.findViewById<TextView>(R.id.txtAC_price).text = item.price.toString()


    }


}