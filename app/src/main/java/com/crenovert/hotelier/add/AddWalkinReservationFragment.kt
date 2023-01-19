package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R

import java.util.*


class AddWalkinReservationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_walkin_reservation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        view.findViewById<TextView>(R.id.txt_booking_time).text = "$month-$day-$year"


        var check_in = view.findViewById<Button>(R.id.btn_check_in_date)
        check_in.setOnClickListener {
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                Toast.makeText(activity?.applicationContext, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
                var msg = "$dayOfMonth - ${monthOfYear + 1} - $year"
                check_in.setText(msg)

            }, year, month, day)
            dpd.show()
        }

        var check_out = view.findViewById<Button>(R.id.btn_check_out_date)
        check_out.setOnClickListener {
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in Toast
                Toast.makeText(activity?.applicationContext, """$dayOfMonth - ${monthOfYear + 1} - $year""", Toast.LENGTH_LONG).show()
                var msg = "$dayOfMonth - ${monthOfYear + 1} - $year"
                check_out.setText(msg)

            }, year, month, day)
            dpd.show()
        }


        var addService = view.findViewById<LinearLayout>(R.id.layout_add_service)
        addService.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.fragment_add_service, null)

            val cancel = dialogLayout.findViewById<TextView>(R.id.cancel_service)
            val save = dialogLayout.findViewById<Button>(R.id.btnS_save)

            cancel.visibility = View.VISIBLE
            builder.setView(dialogLayout)
            dialogLayout.setPadding(30, 30, 30, 30)


            var alertDialog = builder.show()
            cancel.setOnClickListener { alertDialog.dismiss() }
            save.setOnClickListener { alertDialog.dismiss() }

        }

        var chooseRooms = view.findViewById<LinearLayout>(R.id.layout_choose_rooms)
        chooseRooms.setOnClickListener {
            val builder = AlertDialog.Builder(activity)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.alert_choose_room, null)

            val cancel = dialogLayout.findViewById<TextView>(R.id.cancel_choose_rooms)
            val save = dialogLayout.findViewById<Button>(R.id.btn_add_to_cart)

            cancel.visibility = View.VISIBLE
            builder.setView(dialogLayout)
            dialogLayout.setPadding(30, 30, 30, 30)


            var alertDialog = builder.show()
            cancel.setOnClickListener { alertDialog.dismiss() }
            save.setOnClickListener { alertDialog.dismiss() }

        }


    }


}
