package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import com.crenovert.hotelier.AddActivity
import com.crenovert.hotelier.adapter.Bed_Types_adp
import com.crenovert.hotelier.data.Bed_Types_dt
import com.google.firebase.database.*
import java.util.ArrayList
import android.net.ConnectivityManager
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crenovert.hotelier.R
import com.crenovert.hotelier.databinding.FragmentBedTypesBinding


class BedTypesFragment : Fragment() {

    private var _binding: FragmentBedTypesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Bed_Types_adp
    private lateinit var modelList: ArrayList<Bed_Types_dt>
    private lateinit var mReferenceBT: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBedTypesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modelList = ArrayList()

        getFireBaseData()
        loadRecyclerView()


        binding.checkboxSelected.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                binding.buttonDeleteAll.visibility = View.VISIBLE
            else binding.buttonDeleteAll.visibility = View.GONE
            for (posiotion in 0 until modelList.size) {
                modelList[posiotion].checkBox = isChecked
            }
            adapter.notifyDataSetChanged()
        }

        addNewData()
        search()
        checkConnectivity()

        binding.textRetry.setOnClickListener { checkConnectivity() }

    }


    private fun getFireBaseData() {
        mReferenceBT = FirebaseDatabase.getInstance().getReference("bed_types")
        val guestsListener = object : ChildEventListener {

            override fun onCancelled(databaseError: DatabaseError?) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot?, s: String?) {}

            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {
            }

            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
                val item = dataSnapshot?.getValue(Bed_Types_dt::class.java)
                modelList.add(item!!)
                adapter.notifyDataSetChanged()
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val item = dataSnapshot?.getValue(Bed_Types_dt::class.java)
                for (position in 0 until modelList.size) {
                    if (modelList[position].id == item!!.id) {
                        adapter.removeAt(position)
                        Log.e("database", "position : $position")
                    }

                }

            }
        }
        mReferenceBT.addChildEventListener(guestsListener)
    }


    private fun loadRecyclerView() {
        binding.rvBedTypes.layoutManager = LinearLayoutManager(activity)
        adapter = Bed_Types_adp(modelList, requireActivity()) { item: Bed_Types_dt ->
            itemClicked(
                item
            )
        }
        binding.rvBedTypes.adapter = adapter
    }

    //adding new data when fab button is clicked
    private fun addNewData() {
        binding.fabBedTypes.setOnClickListener {
            var intent = Intent(activity?.applicationContext, AddActivity::class.java)
            intent.putExtra("message", "bed_type")
            activity?.startActivity(intent)
        }
    }

    // search data set inorder by input data name
    private fun search() {

        binding.searchBT.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchBT.clearFocus()
                /*if(list.contains(query)){
                    adapter.getFilter().filter(query);
                }else{
                    Toast.makeText(MainActivity.this, "No Match found", Toast.LENGTH_LONG).show();
                }
                */
                return false

            }

            override fun onQueryTextChange(s: String): Boolean {
                var s = s
                s = s.toLowerCase()
                val newList = ArrayList<Bed_Types_dt>()
                for (model in modelList) {
                    val name = model.name.toLowerCase()
                    if (name.contains(s)) {
                        newList.add(model)
                    }
                }
                adapter.setFilter(newList)
                return true
            }
        })
    }


    private fun itemClicked(item: Bed_Types_dt) {


        val builder = AlertDialog.Builder(activity)
        val dialogLayout = layoutInflater.inflate(R.layout.alert_confirm_delete, null)
        builder.setView(dialogLayout)
        var alertDialog = builder.show()


        dialogLayout.findViewById<TextView>(R.id.txtConfirm_cancle).setOnClickListener {
            alertDialog.dismiss()
        }

        dialogLayout.findViewById<Button>(R.id.btnAlert_yes).setOnClickListener {
            try {
                mReferenceBT.child("${item.id}").removeValue()
            } catch (e: Exception) {
                println(e)

            }
            alertDialog.dismiss()

        }

        dialogLayout.findViewById<Button>(R.id.btnAlert_no).setOnClickListener {
            alertDialog.dismiss()
        }

    }

    private fun checkConnectivity() {
        if (isConnected()) {
            binding.rvBedTypes.visibility = View.VISIBLE
            binding.offlineBT.visibility = View.GONE

        } else {
            binding.rvBedTypes.visibility = View.GONE
            binding.offlineBT.visibility = View.VISIBLE
        }
    }

    fun isConnected(): Boolean {
        var connected = false
        try {
            val cm =
                activity?.applicationContext!!.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nInfo = cm.activeNetworkInfo
            connected = (nInfo != null && nInfo.isAvailable && nInfo.isConnected)
            return connected

        } catch (e: Exception) {
            Log.e("Connectivity Exception", e.message)
        }

        return connected
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    override fun onPause() {
        super.onPause()
        checkConnectivity()
    }
}



