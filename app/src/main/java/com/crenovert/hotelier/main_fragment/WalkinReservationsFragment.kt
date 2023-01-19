package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crenovert.hotelier.AddActivity
import com.crenovert.hotelier.EditActivity
import com.crenovert.hotelier.R
import com.crenovert.hotelier.adapter.Walkin_Reservations_adp
import com.crenovert.hotelier.data.Walkin_Reservations_dt
import com.crenovert.hotelier.databinding.FragmentWalkinReservationsBinding
import java.util.ArrayList


class WalkinReservationsFragment : Fragment() {

    private var _binding: FragmentWalkinReservationsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWalkinReservationsBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun generateData(): ArrayList<Walkin_Reservations_dt> {
        val result = ArrayList<Walkin_Reservations_dt>()

        var data = Walkin_Reservations_dt()
        data.checkBox = false
        data.id = 1
        data.name = "Mg Sai Arker"
        data.nrc = "7/PaMaNa(N)211931"
        data.email = "saimyitkyinar123@gmail.com"
        data.ph = "09454457120"
        data.address = "MyitKyinar"
        data.checkin = "07-10-2019"
        data.checkout = "07-15-2019"
        data.total = 200000
        data.paid = 150000
        data.balance = 50000
        data.bookingtime = "07-05-2019"
        data.status = "Check In"
        result.add(data)

        data = Walkin_Reservations_dt()
        data.checkBox = false
        data.id = 2
        data.name = "Mg Tint Mg Mg"
        data.nrc = "7/PaMaNa(N)211931"
        data.email = "tintmgmg123@gmail.com"
        data.ph = "09454457120"
        data.address = "Magway"
        data.checkin = "07-10-2019"
        data.checkout = "07-15-2019"
        data.total = 200000
        data.paid = 150000
        data.balance = 50000
        data.bookingtime = "07-05-2019"
        data.status = "Check In"
        result.add(data)


        data = Walkin_Reservations_dt()
        data.checkBox = false
        data.id = 3
        data.name = "Mg Sai Thein Han"
        data.nrc = "7/PaMaNa(N)211931"
        data.email = "saitheinhan123@gmail.com"
        data.ph = "09454457120"
        data.address = "LarSho"
        data.checkin = "07-10-2019"
        data.checkout = "07-15-2019"
        data.total = 200000
        data.paid = 150000
        data.balance = 50000
        data.bookingtime = "07-05-2019"
        data.status = "New"
        result.add(data)

        data = Walkin_Reservations_dt()
        data.checkBox = false
        data.id = 4
        data.name = "Ma Aye Pyae"
        data.nrc = "7/PaMaNa(N)211931"
        data.email = "ayepyae123@gmail.com"
        data.ph = "09454457120"
        data.address = "Yangon"
        data.checkin = "07-10-2019"
        data.checkout = "07-15-2019"
        data.total = 200000
        data.paid = 150000
        data.balance = 50000
        data.bookingtime = "07-05-2019"
        data.status = "Check Out"
        result.add(data)

        data = Walkin_Reservations_dt()
        data.checkBox = false
        data.id = 5
        data.name = "Mg Myo Thint Win"
        data.nrc = "7/PaMaNa(N)211931"
        data.email = "myothintwin123@gmail.com"
        data.ph = "09454457120"
        data.address = "Mandalay"
        data.checkin = "07-10-2019"
        data.checkout = "07-15-2019"
        data.total = 200000
        data.paid = 150000
        data.balance = 50000
        data.bookingtime = "07-05-2019"
        data.status = "Check Out"
        result.add(data)
        return result
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fabReservations.setOnClickListener {
            var intent = Intent(activity?.applicationContext, AddActivity::class.java)
            intent.putExtra("message", "walkin_reservation")
            activity?.startActivity(intent)
        }

        val data: ArrayList<Walkin_Reservations_dt> = generateData()
        binding.rvWalkinReservation.layoutManager = LinearLayoutManager(activity)
        binding.rvWalkinReservation.adapter = Walkin_Reservations_adp(
            data,
            requireActivity().applicationContext
        ) { item: Walkin_Reservations_dt, msg: String -> itemClicked(item, msg) }



        binding.cbChecked.setOnCheckedChangeListener { buttonView, isChecked ->
            for (posiotion in 0 until data.size) {
                data[posiotion].checkBox = isChecked
            }
            binding.rvWalkinReservation.adapter = Walkin_Reservations_adp(
                data,
                requireActivity().applicationContext
            ) { item: Walkin_Reservations_dt, msg: String -> itemClicked(item, msg) }
        }


    }

    private fun itemClicked(item: Walkin_Reservations_dt, msg: String) {

        when (msg) {
            "edit" -> {
                var intent = Intent(activity?.applicationContext, EditActivity::class.java)
                intent.putExtra("message", "walkin_reservation")
                activity?.startActivity(intent)
            }

            "delete" -> {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Delete reservation records")
                builder.setMessage("Are you sure want to delete .\n Cann\'t be backed up again!")
                builder.setIcon(android.R.drawable.ic_dialog_alert)
                builder.setPositiveButton("Yes") { dialogInterface, which ->
                    Toast.makeText(
                        activity?.applicationContext,
                        "Delete Successful",
                        Toast.LENGTH_LONG
                    ).show()
                }

                builder.setNeutralButton("Cancel") { dialogInterface, which ->
                    Toast.makeText(
                        activity?.applicationContext,
                        "Operation Cancel",
                        Toast.LENGTH_LONG
                    ).show()
                }

                builder.setNegativeButton("No") { dialogInterface, which ->
                    Toast.makeText(activity?.applicationContext, "clicked No", Toast.LENGTH_LONG)
                        .show()
                }

                val alertDialog: AlertDialog = builder.create()
                alertDialog.window.setLayout(200, 200)

                alertDialog.setCancelable(true)
                alertDialog.show()
            }
        }

    }


}
