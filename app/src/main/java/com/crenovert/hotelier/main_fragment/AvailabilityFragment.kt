package com.crenovert.hotelier.main_fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.databinding.FragmentAvailabilityBinding
import java.util.Calendar


class AvailabilityFragment : Fragment() {

    private var _binding: FragmentAvailabilityBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAvailabilityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val msg = "" + dayOfMonth + "/" + (month + 1) + "/" + year
            binding.txtSelectedDate.text = msg
        }


        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        binding.btnStartDate.setOnClickListener {
            val dpd = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in Toast
                    Toast.makeText(
                        activity?.applicationContext,
                        """$dayOfMonth - ${monthOfYear + 1} - $year""",
                        Toast.LENGTH_LONG
                    ).show()
                    var msg = "$dayOfMonth - ${monthOfYear + 1} - $year"
                    binding.btnStartDate.text = msg

                },
                year,
                month,
                day
            )
            dpd.show()
        }

        binding.btnEndDate.setOnClickListener {
            val dpd = DatePickerDialog(
                activity,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in Toast
                    Toast.makeText(
                        activity?.applicationContext,
                        """$dayOfMonth - ${monthOfYear + 1} - $year""",
                        Toast.LENGTH_LONG
                    ).show()
                    var msg = "$dayOfMonth - ${monthOfYear + 1} - $year"
                    binding.btnEndDate.text = msg

                },
                year,
                month,
                day
            )
            dpd.show()

        }

        binding.spinnerACalendar.isEnabled = false

    }
}