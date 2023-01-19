package com.crenovert.hotelier

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crenovert.hotelier.data.Manage_Staff_dt
import com.crenovert.hotelier.data.Permissions_dt
import com.crenovert.hotelier.databinding.ActivityLoginBinding
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var mReferenceMS: DatabaseReference
    private lateinit var mReferenceP: DatabaseReference

    private var spinnerMS = arrayListOf<Manage_Staff_dt>()
    private var spinnerP = arrayListOf<String>()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mReferenceP = FirebaseDatabase.getInstance().getReference("permissions")
        mReferenceMS = FirebaseDatabase.getInstance().getReference("manage_staff")

        spinnerP.add("Choose Role Name")


        getFireBaseData()

        binding.spinnerSIRole.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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


        binding.btnSI.setOnClickListener {

            if (binding.spinnerSIRole.selectedItemPosition == 0 || binding.txtSIEmail.text!!.equals(
                    ""
                ) || binding.txtSIPwd.equals(
                    ""
                )
            ) {
                if (binding.txtSIEmail.text!!.equals(""))
                    binding.txtSIEmail.error = "Enter email"
                if (binding.txtSIPwd.equals(""))
                    binding.txtSIPwd.error = "Enter password"
            } else {
                for (i in spinnerMS) {
                    if (i.email == binding.txtSIEmail.toString() && i.password == binding.txtSIPwd.toString()) {
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("message", binding.spinnerSIRole.selectedItem.toString())
                        startActivity(intent)
                        finish()
                    }
                }


                Toast.makeText(
                    this.applicationContext,
                    "Your\'re not registered in the system",
                    Toast.LENGTH_SHORT
                ).show()

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

                    spinnerP.add(item!!.name)

                }


                val arrayAdapter = ArrayAdapter(
                    this@LoginActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    spinnerP
                )
                binding.spinnerSIRole.adapter = arrayAdapter
            }


        }
        mReferenceP.addListenerForSingleValueEvent(childEventListener)
    }

    private fun addStaffData(position: Int) {

        if (position == 0) {
            binding.txtSIEmail.isEnabled = false
            binding.txtSIPwd.isEnabled = false

        } else {

            binding.txtSIEmail.isEnabled = true
            binding.txtSIPwd.isEnabled = true
            val childEventListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {

                }

                override fun onDataChange(p0: DataSnapshot?) {

                    for (data in p0!!.children) {
                        var item = data.getValue(Manage_Staff_dt::class.java)
                        if (item!!.role == binding.spinnerSIRole.selectedItem.toString()) {
                            spinnerMS.add(item)

                        }
                    }

                }


            }
            mReferenceMS.addListenerForSingleValueEvent(childEventListener)
        }
    }

}
