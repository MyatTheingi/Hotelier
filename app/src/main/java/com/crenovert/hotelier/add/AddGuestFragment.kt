package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentAddGuestBinding
import com.crenovert.hotelier.data.Guests_dt
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddGuestFragment : Fragment() {

    private var _binding: FragmentAddGuestBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReference: DatabaseReference
    private lateinit var data: Guests_dt
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddGuestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReference = FirebaseDatabase.getInstance().getReference("guests")
        data = Guests_dt()

        binding.buttonSave.setOnClickListener {

            if (binding.edittextName.text.toString().isBlank()
                || binding.edittextNrc.text.toString().isBlank()
                || binding.edittextEmail.text.toString().isBlank()
                || binding.edittextPhoneNo.text.toString().isBlank()
                || binding.edittextAddress.text.toString().isBlank()
                || binding.edittextCountry.text.toString().isBlank()
            ) {
                if (binding.edittextName.text.toString().isBlank())
                    binding.edittextName.error = "fill name"

                if (binding.edittextNrc.text.toString().isBlank())
                    binding.edittextNrc.error = "fill nrc"

                if (binding.edittextEmail.text.toString().isBlank())
                    binding.edittextEmail.error = "fill email"

                if (binding.edittextPhoneNo.text.toString().isBlank())
                    binding.edittextPhoneNo.error = "fill name"

                if (binding.edittextAddress.text.toString().isBlank())
                    binding.edittextAddress.error = "fill nrc"

                if (binding.edittextCountry.text.toString().isBlank())
                    binding.edittextCountry.error = "fill email"
                showDialog("error")

            } else {

                data.checkBox = false
                data.id = Integer.parseInt(binding.edittextGuestId.text.toString())
                data.name = binding.edittextName.text.toString()
                data.nrc = binding.edittextNrc.text.toString()
                data.email = binding.edittextEmail.text.toString()
                data.ph = binding.edittextPhoneNo.text.toString()
                data.address = binding.edittextAddress.text.toString()
                data.country = binding.edittextCountry.text.toString()
                saveToDatabase(data)
                showDialog("successful")
            }
        }

    }

    fun saveToDatabase(data: Guests_dt) {

        mReference.child("${data.id}").setValue(data)
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

