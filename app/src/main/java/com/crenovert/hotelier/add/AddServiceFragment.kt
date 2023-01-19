package com.crenovert.hotelier.add


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentAddServiceBinding
import com.crenovert.hotelier.data.Services_dt
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddServiceFragment : Fragment() {

    private var _binding: FragmentAddServiceBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAddServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mReference = FirebaseDatabase.getInstance().getReference("services")
        var data = Services_dt()

        binding.btnSSave.setOnClickListener {

            if (binding.dataSId.text.toString() == "" || binding.dataSName.text.toString() == "" || binding.dataSType.text.toString() == "" || binding.dataSPrice.text.toString() == ""
            ) {
                if (binding.dataSId.text.toString() == "")
                    binding.dataSId.error = "fill id"

                if (binding.dataSName.text.toString() == "")
                    binding.dataSName.error = "fill name"

                if (binding.dataSType.text.toString() == "")
                    binding.dataSType.error = "fill type"

                if (binding.dataSPrice.text.toString() == "")
                    binding.dataSPrice.error = "fill name"

                showDialog("error")

            } else {

                data.checkBox = false
                data.id = Integer.parseInt(binding.dataSId.text.toString())
                data.name = binding.dataSName.text.toString()
                data.type = binding.dataSType.text.toString()
                data.price = Integer.parseInt(binding.dataSPrice.text.toString())

                saveToDatabase(data)
                showDialog("successful")
            }
        }

    }

    fun saveToDatabase(data: Services_dt) {

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
