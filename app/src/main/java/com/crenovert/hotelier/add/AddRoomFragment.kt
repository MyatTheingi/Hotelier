package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentAddRoomBinding
import com.crenovert.hotelier.data.Room_Types_dt
import com.crenovert.hotelier.data.Rooms_dt
import com.google.firebase.database.*


class AddRoomFragment : Fragment() {
    private var _binding: FragmentAddRoomBinding? = null
    private val binding = _binding!!

    private lateinit var mReferenceR: DatabaseReference
    private lateinit var mReferenceRT: DatabaseReference

    private var spinnerRT = arrayListOf<String>()
    private lateinit var data: Rooms_dt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReferenceR = FirebaseDatabase.getInstance().getReference("rooms")

        spinnerRT.add("Choose Room Type")
        getFireBaseData()
        binding.btnRSave.setOnClickListener {


            if (binding.dataRId.text.toString().isBlank() || binding.dataRNo.text.toString()
                    .isBlank() || binding.dataRRoomType.selectedItemPosition == 0 || binding.dataRFloor.text.toString()
                    .isBlank() || binding.dataRStatus.selectedItemPosition == 0
            ) {
                if (binding.dataRId.text.toString().isBlank())
                    binding.dataRId.error = "fill id"
                if (binding.dataRNo.text.toString().isBlank())
                    binding.dataRNo.error = "fill no"
                if (binding.dataRFloor.text.toString().isBlank())
                    binding.dataRFloor.error = "fill floor"

                showDialog("error")
            } else {

                data = Rooms_dt()
                data.checkBox = false
                data.id = Integer.parseInt(binding.dataRId.text.toString())
                data.no = binding.dataRNo.text.toString()
                data.type = binding.dataRRoomType.selectedItem.toString()
                data.floor = binding.dataRFloor.text.toString()
                data.status = binding.dataRStatus.selectedItem.toString()

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
                binding.dataRRoomType.adapter = arrayAdapter

            }


        }
        mReferenceRT.addListenerForSingleValueEvent(childEventListener)
    }


    fun saveToDatabase(data: Rooms_dt) {

        mReferenceR.child("${data.id}").setValue(data)
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
