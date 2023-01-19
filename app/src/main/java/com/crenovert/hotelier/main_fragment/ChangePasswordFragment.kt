package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentChangePasswordBinding


class ChangePasswordFragment : Fragment() {

    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtCPShowpwd.setOnClickListener {
            if (binding.txtCPShowpwd.text.equals("Show Password")) {
                binding.txtCPShowpwd.text = "Hide Password"

            } else {
                binding.txtCPShowpwd.text = "Show Password"
            }
        }

        binding.btnCP.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {

        val builder = AlertDialog.Builder(activity)
        var dialogLayout: View = layoutInflater.inflate(R.layout.alert_warning, null)
        builder.setView(dialogLayout)
        var alertDialog = builder.show()
        dialogLayout.findViewById<Button>(R.id.btnAlert_warning).setOnClickListener {
            alertDialog.dismiss()
        }
    }


}
