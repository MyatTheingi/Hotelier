package com.crenovert.hotelier.add

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.data.Calendar_dt
import com.crenovert.hotelier.data.Room_Types_dt
import com.crenovert.hotelier.databinding.FragmentAddCalendarBinding
import com.google.firebase.database.*


class AddCalendarFragment : Fragment() {

    private var _binding: FragmentAddCalendarBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReferenceC: DatabaseReference
    private lateinit var mReferenceRT: DatabaseReference

    private var spinnerRT = arrayListOf<String>()
    private lateinit var data: Calendar_dt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReferenceC = FirebaseDatabase.getInstance().getReference("calendar")

        spinnerRT.add("Choose Room Type")
        getFireBaseData()

        binding.buttonSave.setOnClickListener {


            if (binding.edittextCalendarId.text.toString().isEmpty()
                || binding.edittextName.text.toString().isEmpty()
                || binding.spinnerRoomType.selectedItemPosition == 0
                || binding.edittextPrice.text.toString().isEmpty()
            ) {
                if (binding.edittextCalendarId.text.toString().isBlank())
                    binding.edittextCalendarId.error = "fill id"
                if (binding.edittextName.text.toString().isBlank())
                    binding.edittextName.error = "fill name"
                if (binding.spinnerRoomType.selectedItemPosition == 0)
                    binding.spinnerRoomType.visibility = View.VISIBLE
                if (binding.edittextPrice.text.toString().isBlank())
                    binding.edittextPrice.error = "fill price"

                showDialog("error")
            } else {

                data = Calendar_dt()
                data.checkBox = false
                data.id = Integer.parseInt(binding.edittextCalendarId.text.toString())
                data.name = binding.edittextName.text.toString()
                data.type = binding.spinnerRoomType.selectedItem.toString()
                data.price = Integer.parseInt(binding.edittextPrice.text.toString())

                binding.textRoomTypeError.visibility = View.GONE

                saveToDatabase(data)
                showDialog("successful")
            }
        }

    }


    private fun getFireBaseData() {

        mReferenceRT = FirebaseDatabase.getInstance().getReference("room_types")

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (data in p0!!.children) {
                    var item = data.getValue(Room_Types_dt::class.java)
                    spinnerRT.add(item!!.name)
                }

                val arrayAdapter =
                    ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerRT)
                binding.spinnerRoomType.adapter = arrayAdapter

            }


        }
        mReferenceRT.addListenerForSingleValueEvent(childEventListener)
    }


    fun saveToDatabase(data: Calendar_dt) {

        mReferenceC.child("${data.id}").setValue(data)
    }

    private fun showDialog(s: String) {

        val builder = AlertDialog.Builder(activity)
        lateinit var dialogLayout: View
        when (s) {
            "error" -> dialogLayout = layoutInflater.inflate(R.layout.alert_error, null)
            "successful" -> dialogLayout = layoutInflater.inflate(R.layout.alert_success, null)
        }
        builder.setView(dialogLayout)
        var alertDialog = builder.show()


        when (s) {
            "error" ->  //try again
                dialogLayout.findViewById<Button>(R.id.btnAlert_tryagain).setOnClickListener {
                    alertDialog.dismiss()

                }
            "successful" ->  //continue
                dialogLayout.findViewById<Button>(R.id.btnAlert_continue).setOnClickListener {
                    alertDialog.dismiss()
                    activity?.finish()
                }
        }

    }
}

