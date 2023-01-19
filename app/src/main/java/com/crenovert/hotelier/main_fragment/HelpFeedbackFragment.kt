package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R


class HelpFeedbackFragment : Fragment(), View.OnClickListener {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnHF_insert -> showDialog()
            R.id.btnHF_send -> showDialog()
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
