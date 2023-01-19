package com.crenovert.hotelier.add

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.data.Bed_Types_dt
import com.crenovert.hotelier.databinding.FragmentAddBedTypeBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddBedTypeFragment : Fragment() {

    private var _binding: FragmentAddBedTypeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReferenceBT: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddBedTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReferenceBT = FirebaseDatabase.getInstance().getReference("bed_types")
        var dataBT = Bed_Types_dt()

        binding.buttonSave.setOnClickListener {

            if (binding.edittextBedTypeId.text.toString().isBlank()
                || binding.edittextName.text.toString().isBlank()
                || binding.edittextDimension.text.toString().isBlank()
            ) {
                if (binding.edittextBedTypeId.text.toString().isBlank())
                    binding.edittextBedTypeId.error = "fill id"

                if (binding.edittextName.text.toString().isBlank())
                    binding.edittextName.error = "fill name"

                if (binding.edittextDimension.text.toString().isBlank())
                    binding.edittextDimension.error = "fill dimension"
                showDialog("error")
            } else {
                dataBT.checkBox = false
                dataBT.id = Integer.parseInt(binding.edittextBedTypeId.text.toString())
                dataBT.name = binding.edittextName.text.toString()
                dataBT.dimen = binding.edittextDimension.text.toString()

                saveToDatabase(dataBT)
                showDialog("successful")
            }
        }

    }

    fun saveToDatabase(data: Bed_Types_dt) {
        mReferenceBT.child("${data.id}").setValue(data)
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
