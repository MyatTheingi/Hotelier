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
import com.crenovert.hotelier.data.Manage_Staff_dt
import com.crenovert.hotelier.data.Permissions_dt
import com.crenovert.hotelier.databinding.FragmentAddManageStaffBinding
import com.google.firebase.database.*


class AddManageStaffFragment : Fragment() {

    private var _binding: FragmentAddManageStaffBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReferenceMS: DatabaseReference
    private lateinit var mReferenceP: DatabaseReference

    private var spinnerP = arrayListOf<String>()
    private lateinit var data: Manage_Staff_dt

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddManageStaffBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReferenceMS = FirebaseDatabase.getInstance().getReference("manage_staff")
        data = Manage_Staff_dt()

        spinnerP.add("Choose Permission")
        getFireBaseData()

        binding.buttonSave.setOnClickListener {

            if (binding.edittextStaffId.text.toString()
                    .isBlank() || binding.edittextName.text.toString()
                    .isBlank() || binding.edittextNrc.text.toString()
                    .isBlank() || binding.edittextEmail.text.toString()
                    .isBlank() || binding.edittextPhoneNo.text.toString()
                    .isBlank() || binding.edittextAddress.text.toString()
                    .isBlank() || binding.edittextPassword.text.toString()
                    .isBlank() || binding.spinnerRole.selectedItemPosition == 0
            ) {


                if (binding.edittextStaffId.text.toString()
                        .isBlank()
                ) binding.edittextStaffId.error = "fill id"

                if (binding.edittextName.text.toString().isBlank())
                    binding.edittextName.error = "fill name"

                if (binding.edittextNrc.text.toString().isBlank())
                    binding.edittextNrc.error = "fill nrc"

                if (binding.edittextEmail.text.toString().isBlank())
                    binding.edittextEmail.error = "fill email"

                if (binding.edittextPhoneNo.text.toString().isBlank())
                    binding.edittextPhoneNo.error = "fill contact no"

                if (binding.edittextAddress.text.toString().isBlank())
                    binding.edittextAddress.error = "fill address"

                if (binding.edittextPassword.text.toString().isBlank())
                    binding.edittextPassword.error = "fill password"

                showDialog("error")

            } else {

                data.checkBox = false
                data.id = Integer.parseInt(binding.edittextStaffId.text.toString())
                data.name = binding.edittextName.text.toString()
                data.nrc = binding.edittextNrc.text.toString()
                data.email = binding.edittextEmail.text.toString()
                data.ph = binding.edittextPhoneNo.text.toString()
                data.address = binding.edittextAddress.text.toString()
                data.password = binding.edittextPassword.text.toString()
                data.role = binding.spinnerRole.selectedItem.toString()


                saveToDatabase(data)
                showDialog("successful")
            }
        }

    }


    private fun getFireBaseData() {

        mReferenceP = FirebaseDatabase.getInstance().getReference("permissions")

        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (data in p0!!.children) {
                    var item = data.getValue(Permissions_dt::class.java)
                    spinnerP.add(item!!.name)
                }

                val arrayAdapter =
                    ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerP)
                binding.spinnerRole.adapter = arrayAdapter

            }


        }
        mReferenceP.addListenerForSingleValueEvent(childEventListener)
    }


    fun saveToDatabase(data: Manage_Staff_dt) {

        mReferenceMS.child("${data.id}").setValue(data)
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
