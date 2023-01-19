package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentAddPermissionBinding
import com.crenovert.hotelier.data.Permissions_dt
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddPermissionFragment : Fragment(), CompoundButton.OnCheckedChangeListener {

    private var _binding: FragmentAddPermissionBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReference: DatabaseReference
    private var checkBoxArray = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mReference = FirebaseDatabase.getInstance().getReference("permissions")

        var data = Permissions_dt()

        binding.checkboxManagement.setOnCheckedChangeListener(this)
        binding.checkboxRoomServices.setOnCheckedChangeListener(this)
        binding.checkboxReceptionCounter.setOnCheckedChangeListener(this)
        binding.checkboxAdministration.setOnCheckedChangeListener(this)

        binding.btnPSave.setOnClickListener {

            if (binding.edittextPermissionId.text.toString()
                    .isBlank() || binding.edittextPermissionName.text.toString()
                    .isBlank() || (!binding.checkboxManagement.isChecked && !binding.checkboxRoomServices.isChecked && !binding.checkboxReceptionCounter.isChecked && !binding.checkboxAdministration.isChecked)
            ) {
                if (binding.edittextPermissionId.text.toString().isBlank())
                    binding.edittextPermissionId.error = "fill id"

                if (binding.edittextPermissionName.text.toString().isBlank())
                    binding.edittextPermissionName.error = "fill name"

                if (!binding.checkboxManagement.isChecked && !binding.checkboxRoomServices.isChecked && !binding.checkboxReceptionCounter.isChecked && !binding.checkboxAdministration.isChecked)


                    showDialog("error")

            } else {

                data.checkBox = false
                data.id = Integer.parseInt(binding.edittextPermissionId.text.toString())
                data.name = binding.edittextPermissionName.text.toString()
                data.permission = checkBoxArray

                saveToDatabase(data)
                showDialog("successful")
            }
        }

    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

        var string: String
        when (buttonView!!.id) {
            R.id.checkbox_management -> {
                string = binding.checkboxManagement.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else
                    checkBoxArray.remove(string)

            }
            R.id.checkbox_room_services -> {
                string = binding.checkboxRoomServices.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.checkbox_reception_counter -> {
                string = binding.checkboxReceptionCounter.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }
            R.id.checkbox_administration -> {
                string = binding.checkboxAdministration.text.toString()
                if (isChecked)
                    checkBoxArray.add(string)
                else checkBoxArray.remove(string)
            }

        }
    }


    fun saveToDatabase(data: Permissions_dt) {

        mReference!!.child("${data.id}").setValue(data)
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
