package com.crenovert.hotelier.main_fragment


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.crenovert.hotelier.AddActivity
import com.crenovert.hotelier.EditActivity
import com.crenovert.hotelier.R
import com.crenovert.hotelier.adapter.Room_Types_adp
import com.crenovert.hotelier.data.Room_Types_dt
import com.crenovert.hotelier.databinding.FragmentRoomTypesBinding
import com.google.firebase.database.*
import java.util.ArrayList


class RoomTypesFragment : Fragment() {

    private var _binding: FragmentRoomTypesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: Room_Types_adp
    private lateinit var modelList: ArrayList<Room_Types_dt>
    private lateinit var mReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomTypesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modelList = ArrayList()

        getFireBaseData()
        loadRecyclerView()

        binding.cbSelected.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked)
                binding.btnDeleteAll.visibility = View.VISIBLE
            else binding.btnDeleteAll.visibility = View.GONE
            for (posiotion in 0 until modelList.size) {
                modelList[posiotion].checkBox = isChecked

            }
            adapter.notifyDataSetChanged()
        }

        addNewData()
        deleteAllData()
        search()
        checkConnectivity()

        binding.txtRTRetry.setOnClickListener { checkConnectivity() }


    }

    //get firebase reference and add listener on it
    private fun getFireBaseData() {

        mReference = FirebaseDatabase.getInstance().getReference("room_types")
        val childEventListener = object : ChildEventListener {

            override fun onCancelled(databaseError: DatabaseError?) {}

            override fun onChildMoved(dataSnapshot: DataSnapshot?, s: String?) {}

            override fun onChildChanged(dataSnapshot: DataSnapshot?, s: String?) {}

            override fun onChildAdded(dataSnapshot: DataSnapshot?, s: String?) {
                val item = dataSnapshot?.getValue(Room_Types_dt::class.java)
                adapter.add(item!!)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
                val item = dataSnapshot?.getValue(Room_Types_dt::class.java)
                for (position in 0 until modelList.size) {
                    if (modelList[position].id == item!!.id) {
                        adapter.removeAt(position)
                    }

                }
            }
        }
        mReference.addChildEventListener(childEventListener)
    }

    //add recycler adapter on recycler view
    private fun loadRecyclerView() {
        binding.rvRoomTypes.layoutManager = LinearLayoutManager(activity)
        adapter = Room_Types_adp(
            modelList,
            requireActivity()
        ) { item: Room_Types_dt, msg: String -> itemClicked(item, msg) }
        binding.rvRoomTypes.adapter = adapter
    }

    //adding new data when fab button is clicked
    private fun addNewData() {
        binding.fabRoomTypes.setOnClickListener {
            var intent = Intent(activity?.applicationContext, AddActivity::class.java)
            intent.putExtra("message", "room_type")
            activity?.startActivity(intent)
        }
    }

    //delete all data on recycler view
    private fun deleteAllData() {

        binding.btnDeleteAll.setOnClickListener {
            val builder = AlertDialog.Builder(requireActivity().applicationContext)
            val dialogLayout = layoutInflater.inflate(R.layout.alert_confirm_delete, null)

            builder.setView(dialogLayout)
            var alertDialog = builder.show()
            dialogLayout.findViewById<TextView>(R.id.txtConfirm_cancle).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogLayout.findViewById<Button>(R.id.btnAlert_yes).setOnClickListener {
                alertDialog.dismiss()
            }
            dialogLayout.findViewById<Button>(R.id.btnAlert_no)
                .setOnClickListener { alertDialog.dismiss() }

        }
    }

    // search data set inorder by input data name
    private fun search() {

        binding.searchRT.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                binding.searchRT!!.clearFocus()
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
                val newList = ArrayList<Room_Types_dt>()
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


    private fun itemClicked(item: Room_Types_dt, msg: String) {

        when (msg) {
            "edit" -> {
                var intent = Intent(activity?.applicationContext, EditActivity::class.java)
                intent.putExtra("message", "room_type")
                activity?.startActivity(intent)
            }

            "delete" -> {
                val builder = AlertDialog.Builder(activity)
                val dialogLayout = layoutInflater.inflate(R.layout.alert_confirm_delete, null)

                builder.setView(dialogLayout)
                var alertDialog = builder.show()

                //cancle deleting the selected data
                dialogLayout.findViewById<TextView>(R.id.txtConfirm_cancle).setOnClickListener {
                    alertDialog.dismiss()
                }
                //delte the record data
                dialogLayout.findViewById<Button>(R.id.btnAlert_yes).setOnClickListener {
                    try {

                        mReference.child("${item.id}").removeValue()
                    } catch (e: Exception) {
                        println(e)

                    }
                    alertDialog.dismiss()
                }

                //do not want to delete the selected data now
                dialogLayout.findViewById<Button>(R.id.btnAlert_no)
                    .setOnClickListener { alertDialog.dismiss() }
            }
        }
    }

    private fun checkConnectivity() {
        if (isConnected()) {
            binding.rvRoomTypes.visibility = View.VISIBLE
            binding.offlineRT.visibility = View.GONE
        } else {
            binding.rvRoomTypes.visibility = View.GONE
            binding.offlineRT.visibility = View.VISIBLE
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

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    override fun onPause() {
        super.onPause()
        checkConnectivity()
    }
}

