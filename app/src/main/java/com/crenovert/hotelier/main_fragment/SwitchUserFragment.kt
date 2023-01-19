package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.Fragment
import com.crenovert.hotelier.R
import com.crenovert.hotelier.data.Manage_Staff_dt
import com.crenovert.hotelier.data.Permissions_dt
import com.crenovert.hotelier.databinding.FragmentSwitchUserBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*


class SwitchUserFragment : Fragment() {

    private var _binding: FragmentSwitchUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var mReferenceMS: DatabaseReference
    private lateinit var mReferenceP: DatabaseReference

    private var spinnerMS = arrayListOf<String>()
    private var spinnerP = arrayListOf<String>()


    private var dataArray = arrayListOf<Manage_Staff_dt>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSwitchUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mReferenceP = FirebaseDatabase.getInstance().getReference("permissions")
        mReferenceMS = FirebaseDatabase.getInstance().getReference("manage_staff")

        binding.txtSUName.isEnabled = false

        spinnerP.add("Choose Role Name")
        spinnerMS.add("Choose Staff")

        getFireBaseData()


        binding.txtSURole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                addStaffData(position)
            }
        }

        binding.txtSUName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                addEmail_Password(position)
            }
        }

        binding.btnSUSignin.setOnClickListener {


            if (binding.txtSURole.selectedItemPosition == 0 || !binding.txtSUName.isEnabled) {

            } else {

                if (isConnected())
                    showDialog()
                else Snackbar.make(requireView(), "check connection", Snackbar.LENGTH_SHORT).show()
            }
        }

    }


    private fun getFireBaseData() {


        val childEventListener = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {

            }

            override fun onDataChange(p0: DataSnapshot?) {

                for (data in p0!!.children) {
                    var item = data.getValue(Permissions_dt::class.java)
                    if (!item!!.permission.contains("Administration Level")) {
                        spinnerP.add(item.name)
                    }
                }


                val arrayAdapter =
                    ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item, spinnerP)
                binding.txtSURole.adapter = arrayAdapter
            }


        }
        mReferenceP.addListenerForSingleValueEvent(childEventListener)
    }

    private fun addStaffData(position: Int) {

        if (position == 0) {
            binding.txtSUName.isEnabled = false
            binding.txtSUName.setSelection(0)

        } else {

            binding.txtSUName.isEnabled = true
            val childEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {

                    for (data in p0!!.children) {
                        var item = data.getValue(Manage_Staff_dt::class.java)
                        if (item!!.role.equals(binding.txtSUName.selectedItem)) {
                            dataArray.add(item)
                            spinnerMS.add(item.name)
                        }
                    }


                    val arrayAdapter = ArrayAdapter(
                        activity,
                        android.R.layout.simple_spinner_dropdown_item,
                        spinnerMS
                    )
                    binding.txtSUName.adapter = arrayAdapter

                }


            }
            mReferenceMS.addListenerForSingleValueEvent(childEventListener)
        }
    }

    private fun addEmail_Password(position: Int) {
        if (position == 0) {
            binding.txtSUEmail.text = "Email"
            binding.txtSUPassword.text = "Password"
        } else {


            for (i in 0 until dataArray.size) {
                if (dataArray[i].name.equals(binding.txtSUName.selectedItem)) {
                    binding.txtSUEmail.text = dataArray[i].email
                    binding.txtSUPassword.text = dataArray[i].password
                }
            }
        }
    }


    fun isConnected(): Boolean {
        var connected = false
        try {
            val cm =
                activity?.applicationContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = nInfo != null && nInfo.isAvailable && nInfo.isConnected
            return connected
        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }

        return connected
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